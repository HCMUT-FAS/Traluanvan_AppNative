package com.example.study;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class PeopleDataSource {
  // Các trường database.
          private SQLiteDatabase database;
  private HowkSQLiteHelper dbHelper;
  private String[] allColumns = {HowkSQLiteHelper.COLUMN_ID,
          HowkSQLiteHelper.COLUMN_PERSON};

  public PeopleDataSource(Context context) {
            dbHelper = new HowkSQLiteHelper(context);
          }

          public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
          }

          public void close() {
            dbHelper.close();
          }

          public Person createPerson(String pName) {
            ContentValues values = new ContentValues();
            values.put(HowkSQLiteHelper.COLUMN_PERSON, pName);
            long insertId = database.insert(HowkSQLiteHelper.TABLE_PEOPLE, null,
                    values);
            Cursor cursor = database.query(HowkSQLiteHelper.TABLE_PEOPLE,
                    allColumns, HowkSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Person newPerson = cursorToPerson(cursor);
            cursor.close();
            return newPerson;
          }

          public void deletePerson(Person p) {
            long id = p.getId();
            Log.e("SQLite", "Person entry deleted with id: " + id);
            database.delete(HowkSQLiteHelper.TABLE_PEOPLE, HowkSQLiteHelper.COLUMN_ID
                    + " = " + id, null);
          }

          public List<Person> getAllPeople() {
            List<Person> people = new ArrayList<Person>();

            Cursor cursor = database.query(HowkSQLiteHelper.TABLE_PEOPLE,allColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
              Person person = cursorToPerson(cursor);
              people.add(person);
              cursor.moveToNext();
            }
            // Nhớ đóng con trỏ lại nhé.
            cursor.close();
            return people;
          }

          private Person cursorToPerson(Cursor cursor) {
            Person person = new Person();
            person.setId(cursor.getLong(0));
            person.setName(cursor.getString(1));
            return person;
          }
        }

