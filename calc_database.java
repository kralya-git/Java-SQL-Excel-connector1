

package com.test.idea;

//импортируем библиотеки для работы с excel
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
//для работы с потоками (будем использовать в блоке с excel)
import  java.io.FileOutputStream;
//для работы с массивами данных
import java.util.Arrays;
//для считывания данных с клавиатуры
import java.util.Scanner;
//для работы с sql
import com.mysql.cj.jdbc.Driver;
//в особенности потом понадобятся Connection, ResultSet и Statement
import java.sql.*;


//главный класс
public class calc_database {

    //точка входа в программу + вывод информации об ошибках с бд
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        //классу scanner присваиваем в качестве аргумента system.in
        Scanner scan = new Scanner(System.in);

        //начальное значение для switch case
        int x = 0;
        String s = "";

        //ввод названия таблицы с клавиатуры
        System.out.println("Введите название таблицы: ");
        String tablename = scan.nextLine();

        //цикл работает до тех пор, пока пользователь не введет 5
        while (!"5".equals(s)) {
            System.out.println();
            System.out.println("1. Вывести все таблицы из текущей БД.");
            System.out.println("2. Создать таблицу в БД.");
            System.out.println("3. Добавить данные в таблицу.");
            System.out.println("4. Сохранить данные в Excel.");
            System.out.println("5. Выйти из программы.");
            s = scan.next();

            //пробуем перевести пользовательский ввод в int
            try {
                x = Integer.parseInt(s);
            }
            //выдаем сообщение об ошибке ввода, и так до тех пор, пока пользователь не введет число
            catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода.");
            }

            //оператор switch для множества развилок
            switch (x) {

                //если пользователь ввел цифру 1, то...
                case 1 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");

                    //statement для выполнения sql запросов
                    //соответственно, createStatement создает этот объект для работы с бд
                    Statement stmt = con.createStatement();

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet rs = stmt.executeQuery("Show tables");
                    System.out.println("Таблицы из текущей БД: ");

                    //rs.next() - построчный вывод названий таблиц в цикле
                    while (rs.next()) {
                        System.out.print(rs.getString(1));
                        System.out.println();
                    }
                }

                //если пользователь ввел цифру 2, то...
                case 2 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");

                    //statement для выполнения sql запросов
                    //соответственно, createStatement создает этот объект для работы с бд
                    Statement stmt1 = con1.createStatement();

                    //задаем запрос СОЗДАНИЯ, как строку
                    String query = "CREATE TABLE IF NOT EXISTS " + tablename +
                            " (num1 float(30,3), num2 float(30,3), сумма float(30, 3), " +
                            "разность float(30,3), произведение float(30,3), " +
                            "частное float(30,3), остаток float(30,3), модуль_num1 float(30,3), " +
                            "модуль_num2 float(30,3), степень float(30,3), " +
                            "num1_степень float(30,3), num2_степень float(30,3))";

                    //отправляем серверу бд sql-выражение
                    //вызваем метод executeQuery объекта Statement и в качестве аргумента передаем скрипт запроса
                    stmt1.executeUpdate(query);

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet rs1 = stmt1.executeQuery("Show tables");
                    System.out.println("Таблицы из текущей БД: ");

                    //rs1.next() - построчный вывод названий таблиц в цикле
                    while (rs1.next()) {
                        System.out.print(rs1.getString(1));
                        System.out.println();
                    }
                }

                //если пользователь ввел цифу 3, то...
                case 3 -> {

                    //регистрируем драйвер для дальнейшей работы (управление jdbc)
                    DriverManager.registerDriver(new Driver());

                    //имя драйвера
                    Class.forName("com.mysql.cj.jdbc.Driver");

                    //пытаемся установить соединение с заданным url бд
                    Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                    System.out.println("Успешно законнектились к БД!");

                    //nextLine() читает всю текущую строки и возвращает всё, что было в этой строке
                    scan.nextLine();

                    //вводим с клавиатуры три числа
                    //метод nextFloat() класса java.util.Scanner сканирует входные данные как Float
                    System.out.println("Введите первое число: ");
                    float num1 = scan.nextFloat();
                    System.out.println("Введите второе число: ");
                    float num2 = scan.nextFloat();
                    System.out.println("Введите степень: ");
                    float step = scan.nextFloat();

                    //задаем запрос ЗАПОЛНЕНИЯ, как строку
                    String query2 = "INSERT INTO " + tablename +
                            " (num1, num2, сумма, разность, произведение, " +
                            "частное, остаток, модуль_num1, модуль_num2, " +
                            "степень, num1_степень, num2_степень) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    //PreparedStatement:
                    //заранее подготавливает запрос с указанием мест, где будут подставляться параметры (знаки '?')
                    //устанавливает параметры определенного типа
                    //и выполняет после этого запрос с уже установленными параметрами
                    PreparedStatement stmt3 = con2.prepareStatement(query2);

                    //установка параметров
                    stmt3.setFloat(1, num1);
                    stmt3.setFloat(2, num2);
                    stmt3.setFloat(3, num1+num2);
                    stmt3.setFloat(4, num1-num2);
                    stmt3.setFloat(5, num1*num2);
                    stmt3.setFloat(6, num1/num2);
                    stmt3.setFloat(7, num1%num2);
                    stmt3.setFloat(8, Math.abs(num1));
                    stmt3.setFloat(9, Math.abs(num2));
                    stmt3.setFloat(10, step);
                    stmt3.setFloat(11, (float) Math.pow(num1, step));
                    stmt3.setFloat(12, (float) Math.pow(num2, step));

                    //выполнение запроса
                    //вызов stmt.executeUpdate() выполняется уже без указания строки запроса
                    stmt3.executeUpdate();
                    System.out.println("Данные в MySQL успешно внесены!");

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet rs2 = stmt3.executeQuery("SELECT * FROM " + tablename + "");
                    System.out.println("Введенные данные: ");

                    //statement для выполнения sql запросов
                    Statement statement = con2.createStatement();

                    //ResultSet - объект java, содержащий результаты выполнения sql запросов
                    ResultSet set = statement.executeQuery("SELECT * FROM " + tablename + " LIMIT 0;");

                    //ResultSetMetaData содержит информацию о результирующей таблице
                    //- количество колонок, тип значений колонок и т.д.
                    ResultSetMetaData data = set.getMetaData();

                    //определяем количество колонок
                    int cnt = data.getColumnCount();

                    //выводим названия колонок через пробел
                    for (int i = 1; i <= cnt; i++) {
                        System.out.print(data.getColumnName(i) + " ");
                    }
                    System.out.print("\n");

                    //rs2.next() - построчный вывод введенных данных в цикле
                    while (rs2.next()) {
                        for (int i = 1; i <= cnt; i++) {
                        System.out.print(Arrays.toString(rs2.getString(i).split("   ")));
                        }
                        System.out.println();
                    }

                    //вывод количества строк в таблице
                    //создаем sql запрос
                    String query = "select count(*) from " + tablename;

                    //пробуем выполнить запрос через try - catch
                    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");
                         Statement stmt = con.createStatement();
                         ResultSet rs = stmt.executeQuery(query)) {
                        while (rs.next()) {
                            int count = rs.getInt(1);
                            System.out.println("Всего внесено строк в таблицу " + tablename + " : " + count);
                        }} catch (SQLException sqlEx) {
                        sqlEx.printStackTrace();
                    }
                }

                //если пользователь ввел цифру 4, то...
                case 4 -> {

                    //реализуем через try - catch, чтобы программы не руинилась в случае ошибки
                    try{

                        //создаем название excel файла с учетом введеного имени таблицы
                        String filename="c:/" + tablename + ".xls" ;

                        //создаём объект HSSFWorkBook
                        HSSFWorkbook hwb = new HSSFWorkbook();

                        //создаём лист, используя на объекте, созданном в предыдущем шаге, createSheet()
                        HSSFSheet sheet =  hwb.createSheet("new sheet");

                        //создаём на листе строку, используя createRow()
                        HSSFRow rowhead = sheet.createRow((short)0);

                        //создаём в строке ячейку — createCell()
                        //задаём значение ячейки через setCellValue()
                        rowhead.createCell((short) 0).setCellValue("num1");
                        rowhead.createCell((short) 1).setCellValue("num2");
                        rowhead.createCell((short) 2).setCellValue("сумма");
                        rowhead.createCell((short) 3).setCellValue("разность");
                        rowhead.createCell((short) 4).setCellValue("произведение");
                        rowhead.createCell((short) 5).setCellValue("частное");
                        rowhead.createCell((short) 6).setCellValue("остаток");
                        rowhead.createCell((short) 7).setCellValue("модуль_num1");
                        rowhead.createCell((short) 8).setCellValue("модуль_num2");
                        rowhead.createCell((short) 9).setCellValue("степень");
                        rowhead.createCell((short) 10).setCellValue("num1_степень");
                        rowhead.createCell((short) 11).setCellValue("num2_степень");

                        //имя драйвера
                        Class.forName("com.mysql.cj.jdbc.Driver");

                        //пытаемся установить соединение с заданным url бд
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "кщще");

                        //statement для выполнения sql запросов
                        Statement st = con.createStatement();

                        //ResultSet - объект java, содержащий результаты выполнения sql запросов
                        ResultSet rs = st.executeQuery("Select * from " + tablename);

                        //начальное значение i для while
                        int i = 1;

                        //создаём в строке ячейку — createCell()
                        //задаём значение ячейки через setCellValue()
                        //и всё это через цикл, чтобы заполнить все строчки
                        while(rs.next()){
                            HSSFRow row = sheet.createRow((short) i);
                            row.createCell((short) 0).setCellValue(Float.toString(rs.getFloat("num1")));
                            row.createCell((short) 1).setCellValue(Float.toString(rs.getFloat("num2")));
                            row.createCell((short) 2).setCellValue(Float.toString(rs.getFloat("сумма")));
                            row.createCell((short) 3).setCellValue(Float.toString(rs.getFloat("разность")));
                            row.createCell((short) 4).setCellValue(Float.toString(rs.getFloat("произведение")));
                            row.createCell((short) 5).setCellValue(Float.toString(rs.getFloat("частное")));
                            row.createCell((short) 6).setCellValue(Float.toString(rs.getFloat("остаток")));
                            row.createCell((short) 7).setCellValue(Float.toString(rs.getFloat("модуль_num1")));
                            row.createCell((short) 8).setCellValue(Float.toString(rs.getFloat("модуль_num2")));
                            row.createCell((short) 9).setCellValue(Float.toString(rs.getFloat("степень")));
                            row.createCell((short) 10).setCellValue(Float.toString(rs.getFloat("num1_степень")));
                            row.createCell((short) 11).setCellValue(Float.toString(rs.getFloat("num2_степень")));
                            i++;
                        }

                        //записываем workbook в file через FileOutputStream
                        FileOutputStream fileOut = new FileOutputStream(filename);

                        //записывает строки в файл
                        hwb.write(fileOut);

                        //закрываем workbook, вызывая close()
                        fileOut.close();
                        System.out.println("Ваш файл Excel успешно сгенерирован!");
                    }

                    //если что-то пойде не так, программа выведет тект ошибки, но не ошибку
                    catch ( Exception ex ) {
                        System.out.println(ex);
                    }
                }
            }
        }
        //если пользователь введет 5, то выйдет из программы
        System.out.println("Вышли из нашей программы.");
    }
}


/*
CREATE DATABASE IF NOT EXISTS mycalculator;
USE mycalculator;

CREATE TABLE sum (
id_sum INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
result FLOAT(10,3) NOT NULL
);

CREATE TABLE difference (
id_difference INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
result FLOAT(10,3) NOT NULL
);

CREATE TABLE multiplication (
id_multiplication INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
result FLOAT(10,3) NOT NULL
);

CREATE TABLE quotient (
id_quotient INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
result FLOAT(10,3) NOT NULL
);

CREATE TABLE rem_div (
id_rem_div INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
result FLOAT(10,3) NOT NULL
);

CREATE TABLE module (
id_module INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
result FLOAT(10,3) NOT NULL
);

CREATE TABLE exponentiation (
id_exponentiation INT NOT NULL AUTO_INCREMENT,
type VARCHAR(10) NOT NULL,
num1 FLOAT(10,3) NOT NULL,
num2 FLOAT(10,3) NOT NULL,
operation VARCHAR(5) NOT NULL,
degree INT NOT NULL,
result FLOAT(10,3) NOT NULL
);
 */