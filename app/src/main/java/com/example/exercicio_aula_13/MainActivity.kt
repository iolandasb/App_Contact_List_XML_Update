package com.example.exercicio_aula_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var nome: EditText
    lateinit var celular: EditText
    lateinit var tipoCadastro: RadioGroup
    lateinit var referencia: EditText
    lateinit var email: EditText
    lateinit var adicional: EditText
    lateinit var salvar: Button
    lateinit var pesquisa: EditText
    lateinit var pesquisar: ImageButton
    lateinit var resultado: TextView
    lateinit var limparPesquisa: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nome = findViewById(R.id.edtNome)
        celular = findViewById(R.id.edtCelular)
        tipoCadastro = findViewById(R.id.rdgTipoCadastro)
        referencia = findViewById(R.id.edtReferencia)
        salvar = findViewById(R.id.btnSalvar)
        pesquisa = findViewById(R.id.edtPesquisa)
        pesquisar = findViewById(R.id.btnPesquisar)
        resultado = findViewById(R.id.txtResultado)
        limparPesquisa = findViewById(R.id.btnLimparPesquisa)

        var cadastro = Cadastro() //Aqui se está chamando a classe "Cadastro"

        salvar.setOnClickListener { //Botão que salva itens na lista.
            //Nos "ifs" abaixo, se está apenas verificando se os campos de "nome", "celular" e "referência" estão vazios. Se estiverem, uma mensagem de erro é criada. Os "ifs" e "elses" estão um dentro do outro para que caso uma das condições não se satisfaça, as demais não possam ocorrer, criando um bloqueio para salvar o contato.
            if (nome.text.isEmpty()) {
                nome.error = "Digite um nome."
            } else if (celular.text.isEmpty()) {
                celular.error = "Digite um número de telefone."
            } else if (referencia.text.isEmpty()) {
                referencia.error = "Digite uma referência."
            } else {
                //No comando abaixo se está chamando a função "adicionarLista" da classe "Cadastro", e colocando dentro dela a frase que se quer montar. Além disso, tudo isso está sendo inserido dentro de uma variável, a qual [e exibida na caixa de texto "resultado" por meio do comando "setText".
                var texto = cadastro.adicionarLista(" " + "-" + " " + nome.text.toString() + " " + celular.text.toString() + " " + referencia.text.toString())
                resultado.setText(texto)
                //Os comandos abaixo servem apenas para limpar as caixas de texto após o clique no botão "salvar".
                nome.text = null
                celular.text = null
                referencia.text = null
            }
        }

        pesquisar.setOnClickListener { //Botão que pesquisa itens na lista.
            val nomeConsultado = pesquisa.text.toString() //Foi inserido numa variável o que é escrito na caixa de texto "pesquisa", a qual deve ser transformada em "string".
            if (nomeConsultado.isNotEmpty()) { //"Se a caixa de texto "pesquisa" NÃO estiver vazia, então..."
                if (cadastro.consultarLista(nomeConsultado).isNotEmpty()) { //"...verificar se quando chamamos a função de "consultarLista" da classe "Cadastro" usando o parâmetro "nomeConsultado" (ou seja, quando digitamos o nome), o RESULTADO da função NÃO voltou vazio (ou seja, foi encontrada um item da lista com a palavra digitada)..."
                    resultado.text = cadastro.consultarLista(nomeConsultado) //"...nesse caso então, mostrar na caixa de texto "resultado" o resultado da função."
                    pesquisa.text = null //Limpar a caixa de texto "pesquisa".
                } else { //"Se o RESULTADO da função for vazio (ou seja, a palavra digitada não encontra correspondente na lista), então..."
                    Toast.makeText( //"...mostrar um aviso de que aquele cadastr não existe."
                        this,
                        "Esse cadastro não consta na agenda!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else pesquisa.error = "Informe um nome para consultar." //"Se a caixa de texto "pesquisa" estiver vazia, então mostrar um erro."
        }

        limparPesquisa.setOnClickListener { //Botão que mostra a lista inteira.
            resultado.text = cadastro.exibirLista() //Aqui se está apenas colocando o resultado da função "exibirLista" da classe "Cadastro" dentro da caixa de texto "resultado".
        }

    }

    fun onRadioButtonClicked(view: View) { //Função com os itens "checáveis".
        if (view is RadioButton) {
            val foiChecado = view.isChecked //Verifica se o item foi "checado".

            when (view.id) { //Quando a view estiver na opção..." -> Aqui já está se inserindo o "id" para indicar que é por meio dele que a opção do layout será chamada.
                R.id.rdPessoal -> //Nome da opção no layout
                    if (foiChecado) { //"Se houve a seleção/checagem da opção..."
                        referencia.setHint("Referência") //"...escreve no "hint" da caixa de texto "Referência"".
                    }
                R.id.rdTrabalho -> { //Nome da opção no layout
                    if (foiChecado) { //"Se houve a seleção/checagem da opção..."
                        referencia.setHint("E-mail") //"...escreve no "hint" da caixa de texto "E-mail"".
                    }
                }
            }
        }
    }

}