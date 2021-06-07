package com.example.exprojetocadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exprojetocadastro.database.LocalDAO;
import com.example.exprojetocadastro.modelo.Local;

public class CadastroLocalActivity extends AppCompatActivity {

    private int id = 0;
    private EditText editTextLocal;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextCapacidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        setTitle("Cadastro de Locais de Eventos");
        editTextLocal = findViewById(R.id.editText_local);
        editTextBairro = findViewById(R.id.editText_bairro);
        editTextCidade = findViewById(R.id.editText_cidade);
        editTextCapacidade = findViewById(R.id.editText_capacidade);
        carregarLocal();
    }

    public void carregarLocal() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("localEdicao") != null) {
            Local local = (Local) intent.getExtras().get("localEdicao");
            editTextLocal.setText(local.getNome_local());
            id = local.getId();
            editTextBairro.setText(local.getBairro());
            editTextCidade.setText(local.getCidade());
            editTextCapacidade.setText(local.getCapacidade());
        }
    }

    public void onClickExcluirLocal (View v) {
        String nome_local = editTextLocal.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String capacidade = editTextCapacidade.getText().toString();
        Local local = new Local(id, nome_local, bairro, cidade, capacidade);
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        boolean excluiu = localDAO.deletar(local);
        if (excluiu) {
            finish();
        } else {
            Toast.makeText(CadastroLocalActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickVoltarLocal (View v) {
        finish();
    }

    public void onClickSalvarLocal (View v) {
        String nome_local = editTextLocal.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String capacidade = editTextCapacidade.getText().toString();
        Local local = new Local(id, nome_local, bairro, cidade, capacidade);
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        boolean salvou = localDAO.salvar(local);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroLocalActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
        }
    }
}