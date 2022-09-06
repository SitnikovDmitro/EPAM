package com.ra.model.service;

import com.ra.model.entity.ChequeLine;
import com.ra.model.enums.Lang;

import java.util.HashMap;

/**
 * This class provides localization of the site
 */
public class TextService {
    private static TextService instance;
    private final HashMap<String, String> ruMap = new HashMap<>();
    private final HashMap<String, String> enMap = new HashMap<>();
    private final HashMap<String, String> ukMap = new HashMap<>();


    public TextService() {
        enMap.put("Add first product", "Add first product");
        enMap.put("Add next product", "Add next product");
        enMap.put("Add this to cheque", "Add this to cheque");
        enMap.put("Cancel", "Cancel");
        enMap.put("Cheque", "Cheque");
        enMap.put("Cheques", "Cheques");
        enMap.put("Code", "Code");
        enMap.put("Cost", "Cost");
        enMap.put("Count", "Count");
        enMap.put("Countable", "Countable");
        enMap.put("Create product", "Create product");
        enMap.put("Create X-report", "Create X-report");
        enMap.put("Create Y-report", "Create Y-report");
        enMap.put("Created", "Created");
        enMap.put("Delete", "Delete");
        enMap.put("Delivery", "Delivery");
        enMap.put("dollars", "dollars");
        enMap.put("dollars per item", "dollars per item");
        enMap.put("dollars per kilogram", "dollars per kilogram");
        enMap.put("dollars per piece", "dollars per piece");
        enMap.put("Finish", "Finish");
        enMap.put("From price", "From price");
        enMap.put("Image file", "Image file");
        enMap.put("kilograms", "kilograms");
        enMap.put("Log out", "Log out");
        enMap.put("Name", "Name");
        enMap.put("No cheques", "No cheques");
        enMap.put("No products found", "No products found");
        enMap.put("Options", "Options");
        enMap.put("Password", "Password");
        enMap.put("pieces", "pieces");
        enMap.put("Price", "Price");
        enMap.put("Product", "Product");
        enMap.put("Product creation", "Product creation");
        enMap.put("Products", "Products");
        enMap.put("Remove", "Remove");
        enMap.put("Search", "Search");
        enMap.put("Senior cashier verification", "Senior cashier verification");
        enMap.put("Sort by date", "Sort by date");
        enMap.put("Sort by price", "Sort by price");
        enMap.put("Specify count of delivery", "Specify count of delivery");
        enMap.put("Specify weight of delivery", "Specify weight of delivery");
        enMap.put("Specify product count", "Specify product count");
        enMap.put("Specify product weight", "Specify product weight");
        enMap.put("Submit", "Submit");
        enMap.put("Title", "Title");
        enMap.put("To price", "To price");
        enMap.put("Total", "Total");
        enMap.put("Total amount", "Total amount");
        enMap.put("Total count", "Total count");
        enMap.put("Total weight", "Total weight");
        enMap.put("Username", "Username");
        enMap.put("Weight", "Weight");


        ukMap.put("Add first product", "Додати перший товар");
        ukMap.put("Add next product", "Дотдати наступний товар");
        ukMap.put("Add this to cheque", "Додати товар у чек");
        ukMap.put("Cancel", "Скасувати");
        ukMap.put("Cheque", "Чек");
        ukMap.put("Cheques", "Чеки");
        ukMap.put("Code", "Код");
        ukMap.put("Cost", "Вартість");
        ukMap.put("Count", "Кількість");
        ukMap.put("Countable", "Зчислюваний");
        ukMap.put("Create product", "Створити товар");
        ukMap.put("Create X-report", "Створити X-звіт");
        ukMap.put("Create Y-report", "Створити Y-звіт");
        ukMap.put("Created", "Створений");
        ukMap.put("Delete", "Видалити");
        ukMap.put("Delivery", "Поставка");
        ukMap.put("dollars", "доларів");
        ukMap.put("dollars per item", "доларів за штуку");
        ukMap.put("dollars per kilogram", "доларів за кілограм");
        ukMap.put("dollars per piece", "доларів за штуку");
        ukMap.put("Finish", "Завершити");
        ukMap.put("From price", "Вартість від");
        ukMap.put("Image file", "Файл зображення");
        ukMap.put("kilograms", "кілограмів");
        ukMap.put("Log out", "Вийти");
        ukMap.put("Name", "Назва");
        ukMap.put("No cheques", "Не знайдено чеків");
        ukMap.put("No products found", "Не знайдено товарів");
        ukMap.put("Options", "Налаштування");
        ukMap.put("Password", "Пароль");
        ukMap.put("pieces", "штук");
        ukMap.put("Price", "Вартість");
        ukMap.put("Product", "Товар");
        ukMap.put("Product creation", "Створення товару");
        ukMap.put("Products", "Товари");
        ukMap.put("Remove", "Видалити");
        ukMap.put("Search", "Шукати");
        ukMap.put("Senior cashier verification", "Підтвердження старшого касира");
        ukMap.put("Sort by date", "Сортувати по даті");
        ukMap.put("Sort by price", "Сортувати по вартості");
        ukMap.put("Specify count of delivery", "Вкажіть кількість поставки");
        ukMap.put("Specify weight of delivery", "Вкажіть вагу поставку");
        ukMap.put("Specify product count", "Вкажіть кількість товару");
        ukMap.put("Specify product weight", "Вкажіть вагу товару");
        ukMap.put("Submit", "Підтвердити");
        ukMap.put("Title", "Назва");
        ukMap.put("To price", "Вартість до");
        ukMap.put("Total", "Загальне");
        ukMap.put("Total amount", "Загальна кількість");
        ukMap.put("Total count", "Загальна кількість");
        ukMap.put("Total weight", "Загальна вага");
        ukMap.put("Username", "Ім'я користувача");
        ukMap.put("Weight", "Вага");

        ruMap.put("Add first product", "Добавить первый товар");
        ruMap.put("Add next product", "Добавить следующий товар");
        ruMap.put("Add this to cheque", "Добавить товар в чек");
        ruMap.put("Cancel", "Отменить");
        ruMap.put("Cheque", "Чек");
        ruMap.put("Cheques", "Чеки");
        ruMap.put("Code", "Код");
        ruMap.put("Cost", "Стоимость");
        ruMap.put("Count", "Количество");
        ruMap.put("Countable", "Перечисляемый");
        ruMap.put("Create product", "Создать товар");
        ruMap.put("Create X-report", "Создать X-отчет");
        ruMap.put("Create Y-report", "Создать Y-отчет");
        ruMap.put("Created", "Созданый");
        ruMap.put("Delete", "Удалить");
        ruMap.put("Delivery", "Поставка");
        ruMap.put("dollars", "долларов");
        ruMap.put("dollars per item", "долларов за штуку");
        ruMap.put("dollars per kilogram", "долларов за килограмм");
        ruMap.put("dollars per piece", "долларов за штуку");
        ruMap.put("Finish", "Завершить");
        ruMap.put("From price", "Стоимость от");
        ruMap.put("Image file", "Файл изображения");
        ruMap.put("kilograms", "килограммов");
        ruMap.put("Log out", "Выйти");
        ruMap.put("Name", "Название");
        ruMap.put("No cheques", "Чеки не найдены");
        ruMap.put("No products found", "Товары не найдены");
        ruMap.put("Options", "Настройки");
        ruMap.put("Password", "Пароль");
        ruMap.put("pieces", "штук");
        ruMap.put("Price", "Стоимость");
        ruMap.put("Product", "Товар");
        ruMap.put("Product creation", "Создание товара");
        ruMap.put("Products", "Товары");
        ruMap.put("Remove", "Удалить");
        ruMap.put("Search", "Искать");
        ruMap.put("Senior cashier verification", "Подтверждение личности старшего кассира");
        ruMap.put("Sort by date", "Сортировать по дате");
        ruMap.put("Sort by price", "Сортировать по цене");
        ruMap.put("Specify count of delivery", "Укажите количество поставки");
        ruMap.put("Specify weight of delivery", "Укажите вес поставки");
        ruMap.put("Specify product count", "Укажите количество товара");
        ruMap.put("Specify product weight", "Укажите вес продута");
        ruMap.put("Submit", "Подтвердить");
        ruMap.put("Title", "Название");
        ruMap.put("To price", "Цена до");
        ruMap.put("Total", "Общее");
        ruMap.put("Total amount", "Общее количество");
        ruMap.put("Total count", "Общее количество");
        ruMap.put("Total weight", "Общий вес");
        ruMap.put("Username", "Имя пользователя");
        ruMap.put("Weight", "Вес");



    }

    /**
     * Returns translated phrase
     * @param key key of word or phrase
     * @param lang target language to translate
     * @return translated phrase
     */
    public String translate(String key, Lang lang) {
        String result = lang==Lang.RU ? ruMap.get(key) : lang==Lang.UK ? ukMap.get(key) : enMap.get(key);
        if (result==null) return "Text not found for : "+key;
        return result;
    }



    public String formatPrice(int price) {
        String txt = Integer.toString(price);
        while (txt.length() < 3) txt = "0" + txt;
        return txt.substring(0, txt.length()-2)+"."+txt.substring(txt.length()-2);
    }

    public String formatWeight(int weight) {
        String txt = Integer.toString(weight);
        while (txt.length() < 4) txt = "0" + txt;
        return txt.substring(0, txt.length()-3)+"."+txt.substring(txt.length()-3);
    }

    public String formatChequeLine(ChequeLine chequeLine, Lang lang) {
        if (chequeLine == null) return "NULL";
        String result = chequeLine.getProduct().getTitle()+" ("+chequeLine.getProduct().getCode()+") - ";
        if (chequeLine.getProduct().isCountable()) {
            result += chequeLine.getAmount() + " "+translate("pieces", lang)+", ";
            result += formatPrice(chequeLine.getProduct().getPrice()*chequeLine.getAmount())+" "+translate("dollars", lang);
        } else {
            result += formatWeight(chequeLine.getAmount()) + " "+translate("kilograms", lang)+", ";
            result += formatPrice(chequeLine.getProduct().getPrice()*chequeLine.getAmount()/1000)+" "+translate("dollars", lang);
        }
        return result;
    }



    public static TextService getInstance() {
        if (instance == null) instance = new TextService();
        return instance;
    }
}
