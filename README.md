# Repositório do aplicativo Viajabessa Viagens

Este repositório foi feito para armazenar o aplicativo para android da empresa Viajabessa. Esta empresa é fictícia e criada apenas para o desafio de se fazer um aplicativo Android que faz requests de uma API mock do Apiary.

## API

A API utilizada foi criada utilizando o serviço [Apiary](https://apiary.io/). E seu endereço para este protótipo é: 
https://private-9ad56-viajabessa83.apiary-mock.com 

A API é descrita utilizando a API Blueprint:

FORMAT: 1A

\# viajabessa

Viajabessa é a api utilizada no aplicativo Android Viajabessa Viagens da empresa Viajabessa.

\## Pacotes de viagens [/pacotes]

\### Lista pacotes [POST]

+ Response 200 ("text/plain; charset=UTF-8")

        [
            {
            "pacote": "Lençóis Maranhenses",
            "valor": 5500.00,
            "link_imagem": "https://www.descubraturismo.com.br/wp-content/uploads/2018/05/lencois-maranhenses-cabure.jpg",
            "pacote_id": 1,
            "descricao": "Um ótimo local para aproveitar o outono!",
            "localizacao": "https://globalstorybook.org/wp-content/uploads/2016/06/brazil-map.jpg"
            }, 
            {
            "pacote": "Guaratuba",
            "valor": 3400.00,
            "link_imagem": "https://www.jblitoral.com.br/wp-content/uploads/2020/04/guaratuba.jpg",
            "pacote_id": 2,
            "descricao": "Um local perfeito para visitar durante o calor do verão!",
            "localizacao": "https://diretorios-mapas.s3.amazonaws.com/41297.png"
            }, 
            {
            "pacote": "Los Angeles",
            "valor": 28000.00,
            "link_imagem": "https://cdn.thecrazytourist.com/wp-content/uploads/2017/11/ccimage-shutterstock_609196643.jpg",
            "pacote_id": 3,
            "descricao": "Um paraíso durante o ano todo",
            "localizacao": "https://laautoshow.com/wp-content/uploads/2017/05/google-map-dtla-lacc-v3.png"
            }
        ]
        
        