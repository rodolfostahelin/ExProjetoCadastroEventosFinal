package com.example.exprojetocadastro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.exprojetocadastro.database.EventoDAO;
import com.example.exprojetocadastro.database.LocalDAO;
import com.example.exprojetocadastro.modelo.Evento;
import com.example.exprojetocadastro.modelo.Local;

import java.util.Date;

public class CadastroEvento extends AppCompatActivity {

    private  int id = 0;
    private Spinner spinnerLocais;
    private ArrayAdapter<Local> locaisAdapter;
    private EditText editTextNome;
    private EditText editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Add Evento");
        spinnerLocais = findViewById(R.id.spinner_locais);
        editTextNome = findViewById(R.id.edtNome);
        editTextData = findViewById(R.id.edtData);
        carregarLocais();
        carregarEvento();

    }

    private void carregarLocais() {
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        locaisAdapter = new ArrayAdapter<Local>(CadastroEvento.this,
                android.R.layout.simple_spinner_item,
                localDAO.listar());
        spinnerLocais.setAdapter(locaisAdapter);
    }

    private void carregarEvento(){

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("eventoEdicao")!=null){
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData());
            int posicaoLocal = obterPosicaoLocal(evento.getLocal());
            spinnerLocais.setSelection(posicaoLocal);
            id = evento.getId();
        }
    }

    private int obterPosicaoLocal (Local local) {
        for (int posicao = 0; posicao < locaisAdapter.getCount(); posicao++) {
            if (locaisAdapter.getItem(posicao).getId() == local.getId()) {
                return posicao;
            }
        }
        return 0;
    }

    private boolean isCampoVazio (String v){
        boolean resultado = (TextUtils.isEmpty(v) || v.trim().isEmpty());
        return resultado;
    }

    public void onClickExcluir(View v) {
        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        int posicaoLocal = spinnerLocais.getSelectedItemPosition();
        Local local = (Local) locaisAdapter.getItem(posicaoLocal);
        Evento evento = new Evento(id,nome,data,local);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean ex = eventoDAO.deletar(evento);
        if (ex){
            finish();
        }
        else{
            Toast.makeText(CadastroEvento.this,"Erro na exclusão!",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void onClickVoltar(View v){finish();}

    public void onClickSalvar(View v){
        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        int posicaoLocal = spinnerLocais.getSelectedItemPosition();
        Local local = (Local) locaisAdapter.getItem(posicaoLocal);
        Evento evento = new Evento(id,nome,data, local);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean salvo = eventoDAO.salvar(evento);
        if (salvo){
            finish();
        }
        else {
            Toast.makeText(CadastroEvento.this,"Erro ao executar!",Toast.LENGTH_LONG).show();
        }
    }
}