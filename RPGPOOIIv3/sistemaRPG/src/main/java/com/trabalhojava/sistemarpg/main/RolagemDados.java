package com.trabalhojava.sistemarpg.main;
/*1° Refatoração
 Autor: Mateus de Oliveira Lopes
 Uso do Extract Class para extrair os trechos de rolagem aleatória do MenusController para uma classe prova RolagemDados
 Objetivo: Diminuir o tamanho do MenusController e melhorar a leitura e organização do código
*/
public class RolagemDados {
    public int testeAtributo(int atributo)
    {
        return (int)(Math.random() * 20) + 1 + atributo;
    }

    public int rodarAtributos()
    {
        int menor = 7;
        int atual;
        int soma = 0;
        for(int i = 0; i < 4; i++)
        {
            atual = (int)(Math.random() * 6) + 1;
            soma = soma + atual;
            if(menor > atual){
                menor = atual;
            }
        }

        return soma - menor;
    }
}
