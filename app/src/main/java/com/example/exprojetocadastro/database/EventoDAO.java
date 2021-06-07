package com.example.exprojetocadastro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.exprojetocadastro.database.entity.EventoEntity;
import com.example.exprojetocadastro.database.entity.LocalEntity;
import com.example.exprojetocadastro.modelo.Evento;
import com.example.exprojetocadastro.modelo.Local;

import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private final String SQL_SELECIONAR = " SELECT evento._id, nome, data, id_local, nome_local," +
            " bairro, cidade, capacidade FROM " + EventoEntity.TABLE_NAME +
            " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " + EventoEntity.COLUMN_NAME_ID_LOCAL +
            " = " + LocalEntity.TABLE_NAME + "." +  LocalEntity._ID;
    private DbGateway dbGateway;

    public EventoDAO(Context context){
        dbGateway = DbGateway.getInstance(context);
    }

    public boolean salvar (Evento evento){
        // Valor das colunas que eu quero enviar
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, evento.getLocal().getId());
        if (evento.getId() > 0){
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,EventoEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0 ;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null,contentValues) > 0;
    }
    public List<Evento> relacionar (){
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_SELECIONAR,null);

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nome_local = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_NOME_LOCAL));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            String capacidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            Local local = new Local(idLocal, nome_local, bairro, cidade, capacidade);
            eventos.add(new Evento(id,nome,data,local));

        }
        cursor.close();
        return eventos;
    }

    public boolean deletar (Evento evento){

        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, evento.getLocal().getId());

        if (evento.getId() > 0){

            return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0 ;
        }
        return dbGateway.getDatabase().replace(EventoEntity.TABLE_NAME,
                null,contentValues) > 0;
    }

}
