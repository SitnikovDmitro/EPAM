package app.servlets;

import app.entity.Cheque;
import app.entity.ChequeLine;
import app.entity.Product;
import app.dao.ChequeDAOImpl;
import app.dao.ChequeLineDAOImpl;
import app.dao.ProductDAOImpl;
import app.dao.UserDAOImpl;
import app.entity.UserX;
import app.service.*;
import app.exceptions.DBException;
import app.exceptions.EmptyParameterException;
import app.exceptions.InvalidParameterException;
import app.localization.Lang;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;


@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserX.class);

    private String filePath;

    private ChequeLineService chequeLineService;
    private ChequeService chequeService;
    private JsonService jsonService;
    private ProductService productService;
    private UserService userService;

    @Override
    public void init() {
        filePath = getServletContext().getInitParameter("file-upload");

        chequeLineService = new ChequeLineService(new ProductDAOImpl());
        chequeService = new ChequeService(new ChequeDAOImpl(), new ChequeLineDAOImpl());
        jsonService = new JsonService();
        productService = new ProductService(new ProductDAOImpl());
        userService = new UserService(new UserDAOImpl());
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");

        try {
            execute(req, resp);
        } catch (ServletException | IOException exception) {
            throw exception;
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(Level.ERROR, exception);
            resp.sendRedirect("controller?action=error&code=500&Server error");
            //logout(req, resp, SiteText.getInstance().translate("error_message", (Lang)req.getSession().getAttribute("lg")));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        try {
            execute(req, resp);
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(Level.ERROR, exception);
            resp.sendRedirect("controller?action=error&code=500&Server error");
            //logout(req, resp, SiteText.getInstance().translate("error_message", (Lang)req.getSession().getAttribute("lg")));
        }
    }

    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, NoSuchMethodException, InvalidParameterException, DBException, EmptyParameterException, InvocationTargetException, IllegalAccessException {
        String action = req.getParameter("action");

        req.getSession().setAttribute("text", TextService.getInstance());
        if (req.getSession().getAttribute("lang") == null) {
            req.getSession().setAttribute("lang", Lang.EN);
        }

        try {
            Method method = Controller.class.getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            if (!method.isAnnotationPresent(HttpRequestMapping.class)) {
                logger.error("No annonation");
                throw new NoSuchMethodException();
            }
            if (method.isAnnotationPresent(CashierRequired.class)) {
                UserX user = (UserX)req.getSession().getAttribute("loggedUser");
                if (user == null || user.getRole() != 1) {
                    resp.sendRedirect("controller?action=error&code=401&description=Unauthorized");
                    return;
                }
            }
            if (method.isAnnotationPresent(MerchandiserRequired.class)) {
                UserX user = (UserX)req.getSession().getAttribute("loggedUser");
                if (user == null || user.getRole() != 3) {
                    resp.sendRedirect("controller?action=error&code=401&description=Unauthorized");
                    return;
                }
            }
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException exception) {
            logger.error("No method for "+action);
            resp.sendRedirect("controller?action=error&code=404&Invalid command");
        }
    }

    @HttpRequestMapping
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, DBException {
        String password = req.getParameter("password");
        String username = req.getParameter("username");

        UserX user = userService.getUser(username, password);

        if (user != null && (user.getRole() == 1 || user.getRole() == 3)) {
            req.getSession().setAttribute("loggedUser", user);
            resp.getWriter().write(jsonService.getJsonWithIntResult(user.getRole()));
        } else {
            resp.getWriter().write(jsonService.getJsonWithIntResult(-1));
        }
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void deleteMerchandiserProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, EmptyParameterException, InvalidParameterException, DBException {
        String productCode = req.getParameter("productCode");

        productService.setProductAsRemovedByCode(productCode);

        req.getSession().setAttribute("products", null);
        resp.sendRedirect("controller?action=showMerchandiserProducts&page=1");
    }

    @CashierRequired
    @HttpRequestMapping
    public void finishCashierCheque(HttpServletRequest req, HttpServletResponse resp) throws IOException, DBException {
        ArrayList<ChequeLine> chequeLines = (ArrayList<ChequeLine>)req.getSession().getAttribute("activeChequeLines");

        chequeService.completeCheque(chequeLines);

        req.getSession().setAttribute("activeChequeLines", null);
        req.getSession().setAttribute("cheques", null);
        req.getSession().setAttribute("chequeLines", null);
        resp.sendRedirect("controller?action=showCashierCheque");
    }

    @CashierRequired
    @HttpRequestMapping
    public void removeCashierCheque(HttpServletRequest req, HttpServletResponse resp) throws IOException, DBException {
        String password = req.getParameter("password");
        String username = req.getParameter("username");

        if (userService.getAccess(username, password)) {
            req.getSession().setAttribute("activeChequeLines", null);
            resp.getWriter().write(jsonService.getJsonWithBooleanResult(true));
        } else {
            resp.getWriter().write(jsonService.getJsonWithBooleanResult(false));
        }
    }

    @CashierRequired
    @HttpRequestMapping
    protected void removeCashierProductFromCheque(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvalidParameterException, DBException {
        ArrayList<ChequeLine> chequeLines = (ArrayList<ChequeLine>)req.getSession().getAttribute("activeChequeLines");
        String productCode = req.getParameter("productCode");
        String password = req.getParameter("password");
        String username = req.getParameter("username");

        if (userService.getAccess(username, password)) {
            chequeLineService.removeChequeLineFromActiveCheque(chequeLines, productCode);
            resp.getWriter().write(jsonService.getJsonWithBooleanResult(true));
        } else {
            resp.getWriter().write(jsonService.getJsonWithBooleanResult(false));
        }
    }

    @CashierRequired
    @HttpRequestMapping
    public void createCashierReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, DBException {
        String reportType = req.getParameter("reportType");
        String password = req.getParameter("password");
        String username = req.getParameter("username");

        if (userService.getAccess(username, password)) {
            int code = chequeService.createReport(reportType, filePath);
            req.getSession().setAttribute("cheques", null);
            req.getSession().setAttribute("chequeLines", null);
            resp.getWriter().write(jsonService.getJsonWithIntResult(code));
        } else {
            resp.getWriter().write(jsonService.getJsonWithIntResult(-1));
        }
    }

    @CashierRequired
    @HttpRequestMapping
    public void getCashierLastReport(HttpServletRequest req, HttpServletResponse resp) throws IOException, DBException {
        String number = req.getParameter("number");

        ServletOutputStream out = resp.getOutputStream();
        byte[] byteArray = Files.readAllBytes(Path.of(filePath+"\\reports\\"+number+".docx"));
        resp.setContentType("application/vnd.ms-word");
        resp.setHeader("Content-Disposition", "attachment; filename=\"Report.docx\"");
        out.write(byteArray);
        out.flush();
        out.close();
    }

    @CashierRequired
    @HttpRequestMapping
    public void addCashierProductToCheque(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, InvalidParameterException, DBException {
        ArrayList<ChequeLine> chequeLines = (ArrayList<ChequeLine>)req.getSession().getAttribute("activeChequeLines");
        String productCode = req.getParameter("productCode");
        String amount = req.getParameter("amount");

        chequeLineService.addChequeLineToActiveCheque(chequeLines, productCode, amount);

        resp.sendRedirect("controller?action=showCashierCheque");
    }

    @CashierRequired
    @HttpRequestMapping
    public void showCashierCheque(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        ArrayList<ChequeLine> chequeLines = (ArrayList<ChequeLine>)req.getSession().getAttribute("activeChequeLines");
        if (chequeLines == null) {
            chequeLines = new ArrayList<>();
            req.getSession().setAttribute("activeChequeLines", chequeLines);
        }

        req.setAttribute("price", chequeLineService.computePrice(chequeLines));
        req.setAttribute("chequeLines", chequeLines);
        req.getRequestDispatcher("cashier/cheque.jsp").forward(req, resp);
    }

    @CashierRequired
    @HttpRequestMapping
    public void setCashierProducts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, InvalidParameterException, DBException {
        String name = req.getParameter("name");
        String code = req.getParameter("code");
        ArrayList<Product> products = new ArrayList<>();

        productService.findProducts(name, code, products);

        req.getSession().setAttribute("products", products);
        req.getSession().setAttribute("name", name);
        req.getSession().setAttribute("code", code);
        resp.sendRedirect("controller?action=showCashierProducts&page=1");
    }

    @CashierRequired
    @HttpRequestMapping
    public void showCashierProducts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, InvalidParameterException, DBException {
        String page = req.getParameter("page");
        ArrayList<Product> products = (ArrayList<Product>)req.getSession().getAttribute("products");

        if (products == null) {
            products = new ArrayList<>();
            productService.findProducts(null, null, products);
            req.getSession().setAttribute("products", products);
        }

        ArrayList<Product> pageProducts = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        productService.findProducts(page, products, pageProducts, pages);

        req.setAttribute("products", pageProducts);
        req.setAttribute("pages", pages);
        req.getRequestDispatcher("cashier/products.jsp").forward(req, resp);
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void setMerchandiserProducts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, InvalidParameterException, DBException {
        String name = req.getParameter("name"), code = req.getParameter("code");
        ArrayList<Product> products = new ArrayList<>();

        productService.findProducts(name, code, products);

        req.getSession().setAttribute("products", products);
        req.getSession().setAttribute("name", name);
        req.getSession().setAttribute("code", code);
        resp.sendRedirect("controller?action=showMerchandiserProducts&page=1");
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void showMerchandiserProducts(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, InvalidParameterException, DBException {
        String page = req.getParameter("page");
        ArrayList<Product> products = (ArrayList<Product>)req.getSession().getAttribute("products");

        if (products == null) {
            products = new ArrayList<>();
            productService.findProducts("", "", products);
            req.getSession().setAttribute("products", products);
        }

        ArrayList<Product> pageProducts = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        productService.findProducts(page, products, pageProducts, pages);

        req.setAttribute("products", pageProducts);
        req.setAttribute("pages", pages);
        req.getRequestDispatcher("merchandiser/products.jsp").forward(req, resp);
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void showMerchandiserProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        req.getRequestDispatcher("merchandiser/product.jsp").forward(req, resp);
    }

    @CashierRequired
    @HttpRequestMapping
    public void showCashierCheques(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        String page = req.getParameter("page");
        ArrayList<Cheque> cheques = (ArrayList<Cheque>)req.getSession().getAttribute("cheques");
        ArrayList<ArrayList<ChequeLine>> chequeLines = (ArrayList<ArrayList<ChequeLine>>)req.getSession().getAttribute("chequeLines");

        if (cheques == null || chequeLines == null) {
            cheques = new ArrayList<>();
            chequeLines = new ArrayList<>();
            chequeService.findCheques(null, null, null, cheques, chequeLines);
            req.getSession().setAttribute("cheques", cheques);
            req.getSession().setAttribute("chequeLines", chequeLines);
        }

        ArrayList<Cheque> pageCheques = new ArrayList<>();
        ArrayList<ArrayList<ChequeLine>> pageChequeLines = new ArrayList<>();
        ArrayList<Integer> pages = new ArrayList<>();

        chequeService.findCheques(page, cheques, chequeLines, pageCheques, pageChequeLines, pages);

        req.setAttribute("cheques", pageCheques);
        req.setAttribute("chequeLines", pageChequeLines);
        req.setAttribute("pages", pages);

        req.getRequestDispatcher("cashier/cheques.jsp").forward(req, resp);
    }

    @CashierRequired
    @HttpRequestMapping
    public void setCashierCheques(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        String fromPrice = req.getParameter("fromPrice");
        String toPrice = req.getParameter("toPrice");
        String sortCriteria = req.getParameter("sortCriteria");

        ArrayList<Cheque> cheques = new ArrayList<>();
        ArrayList<ArrayList<ChequeLine>> chequeLines = new ArrayList<>();

        chequeService.findCheques(fromPrice, toPrice, sortCriteria, cheques, chequeLines);

        req.getSession().setAttribute("fromPrice", fromPrice);
        req.getSession().setAttribute("toPrice", toPrice);
        req.getSession().setAttribute("sortCriteria", sortCriteria);
        req.getSession().setAttribute("cheques", cheques);
        req.getSession().setAttribute("chequeLines", chequeLines);
        resp.sendRedirect("controller?action=showCashierCheques&page=1");
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void createMerchandiserProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, EmptyParameterException, InvalidParameterException, DBException {
        String title = req.getParameter("title");
        String countable = req.getParameter("countable");
        String amount = req.getParameter("amount");
        String price = req.getParameter("price");
        Part image = req.getPart("image");

        productService.createProduct(filePath+"images", title, amount, price, countable, image);

        req.getSession().setAttribute("products", null);
        resp.sendRedirect("controller?action=showMerchandiserProducts&page=1");
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void deliverMerchandiserProduct(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, EmptyParameterException, InvalidParameterException, DBException {
        String productCode = req.getParameter("productCode");
        String productAmount = req.getParameter("amount");

        productService.deliverProduct(productCode, productAmount);

        req.getSession().setAttribute("products", null);
        resp.sendRedirect("controller?action=showMerchandiserProducts&page=1");
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void changeMerchandiserLanguage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, EmptyParameterException, InvalidParameterException, DBException {
        String lang = req.getParameter("lang");

        req.getSession().setAttribute("lang", "UK".equals(lang) ? Lang.UK : "RU".equals(lang) ? Lang.RU : Lang.EN);

        resp.sendRedirect("controller?action=showMerchandiserOptions");
    }

    @CashierRequired
    @HttpRequestMapping
    public void changeCashierLanguage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, EmptyParameterException, InvalidParameterException, DBException {
        String lang = req.getParameter("lang");

        req.getSession().setAttribute("lang", "UK".equals(lang) ? Lang.UK : "RU".equals(lang) ? Lang.RU : Lang.EN);

        resp.sendRedirect("controller?action=showCashierOptions");
    }

    @MerchandiserRequired
    @HttpRequestMapping
    public void showMerchandiserOptions(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, EmptyParameterException, InvalidParameterException, DBException {
        resp.sendRedirect("merchandiser/options.jsp");
    }

    @CashierRequired
    @HttpRequestMapping
    public void showCashierOptions(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException, EmptyParameterException, InvalidParameterException, DBException {
        resp.sendRedirect("cashier/options.jsp");
    }

    @HttpRequestMapping
    public void showErrorPage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        req.setAttribute("message", req.getParameter("message"));
        resp.sendRedirect(req.getContextPath());
    }

    @HttpRequestMapping
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().setAttribute("loggedUser", null);
        resp.sendRedirect(req.getContextPath());
    }


    public void logout(HttpServletRequest req, HttpServletResponse resp, String message) throws IOException, ServletException {
        req.setAttribute("message", message);
        req.getSession().setAttribute("loggedUser", null);
        req.getRequestDispatcher("").forward(req, resp);
    }

    @HttpRequestMapping
    public void error(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setAttribute("code", req.getParameter("code"));
        req.setAttribute("description", req.getParameter("description"));

        req.getRequestDispatcher("common/error.jsp").forward(req, resp);
    }
}