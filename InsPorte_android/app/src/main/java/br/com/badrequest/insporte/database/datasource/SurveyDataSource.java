package br.com.badrequest.insporte.database.datasource;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.badrequest.insporte.bean.Option;
import br.com.badrequest.insporte.bean.Question;
import br.com.badrequest.insporte.bean.SurveyType;
import br.com.badrequest.insporte.util.Constants;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class SurveyDataSource extends SQLiteAssetHelper {

    private final String QRY_QUESTIONS = "SELECT * FROM pergunta WHERE id_questionario = ?";
    private final String QRY_QUESTION = "SELECT * FROM pergunta WHERE id = ?";
    private final String QRY_OPTIONS = "SELECT * FROM opcao WHERE id_pergunta = ?";
    private final String QRY_OPTION = "SELECT * FROM opcao WHERE id = ?";

    public SurveyDataSource(Context context) {
        super(context, Constants.DATABASE_NAME,
                null,
                null, Constants.DATABASE_VERSION);
        setForcedUpgradeVersion(Constants.DATABASE_VERSION);
    }

    public List<Question> listSurvey(SurveyType surveyType) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        List<Question> questionList = new ArrayList<Question>();
        try {
            cursor = db.rawQuery(QRY_QUESTIONS, new String[] {String.valueOf(surveyType.getId())});
            while (cursor.moveToNext()) {
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setOptions(getOptions(cursor.getInt(0)));
                question.setQuestion(cursor.getString(2));
                questionList.add(question);
            }

            return questionList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public Question getQuestion(int idQuestion) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        Question question = null;
        try {
            cursor = db.rawQuery(QRY_QUESTION, new String[] {String.valueOf(idQuestion)});
            if(cursor.moveToNext()) {
                question = new Question();
                question.setId(cursor.getInt(0));
                question.setOptions(getOptions(cursor.getInt(0)));
                question.setQuestion(cursor.getString(2));
            }

            return question;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }


    private List<Option> getOptions(int idQuestion) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        List<Option> optionList = new ArrayList<Option>();
        try {
            cursor = db.rawQuery(QRY_OPTIONS, new String[] {String.valueOf(idQuestion)});
            while (cursor.moveToNext()) {
                Option option = new Option();
                option.setId(cursor.getInt(0));
                option.setText(cursor.getString(2));
                optionList.add(option);
            }

            return optionList;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public Option getOption(int idOption) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;

        Option option = null;
        try {
            cursor = db.rawQuery(QRY_OPTION, new String[] {String.valueOf(idOption)});
            if(cursor.moveToNext()) {
                option = new Option();
                option.setId(cursor.getInt(0));
                option.setText(cursor.getString(2));
            }

            return option;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }
}
