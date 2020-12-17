# SuperTrivia
## Trabalho final de Dispositivos Moveis

1. Desenvolva uma aplicativo para resolução de quizzes (jogo de perguntas e respostas).
2. Uma API foi integralmente desenvolvida para tal: https://super-trivia-server.herokuapp.com. Utilize o Postman ou software semelhante para estudar e entender a API.
3. No primeiro acesso, o usuário vê uma tela de login (E-mail, Senha) e uma opção para cadastrar-se (Nome, E-mail, Senha e Confirmar Senha).
Obs: Uma vez autenticado, das próximas vezes que for usar o aplicativo, o usuário não deve ver o login.
4. Ao autenticar-se, o usuário pode começar a jogar, mas antes escolhe uma dificuldade (difícil, médio ou fácil) e uma categoria. Ele tem a opção de não escolher, desta forma a API irá aleatorizar as perguntas. Utilizar RecyclerView para categorias que devem ser carregadas da API.
5. O aplicativo apresenta uma pergunta por vez de acordo com as seleções que o usuário fez no início. Cada questão é apresentada a ele com uma pergunta e suas quatro respostas.
Obs: a API já se responsabiliza por gerar perguntas aleatórios, com suas opções de respostas embaralhadas.
6. Ao selecionar uma das respostas, o aplicativo deve apresentar ao usuário, de maneira clara, se houve erro ou acerto, bem como a suas pontuação atualizada.
7. Após cada pergunta, o jogo deve solicitar ao usuário se ele deseja uma próxima pergunta ou se ele deseja encerrar a partida.
8. Ao selecionar a segunda opção, uma tela de resumo da partida do usuário deve ser apresentada.
Obs: Nem todas estas informações são fornecidas pelo servidor. Você deve arquivos dados localmente para quando este momento chegar.
9. A qualquer momento o usuário pode acessar a tela de configurações mostrada no início da partida para alterar a dificuldade e a categoria das próximas perguntas (utilizar bottom navigation ou componente semelhante).
10. A qualquer momento o usuário pode acessar o ranking, que mostra as 20 melhores pontuações da história do jogo dele e dos demais usuários (utilizar bottom navigation ou componente semelhante).

## Observações
- Deve ser feito individualmente (cópias não serão toleradas).
- Apesar de existir armazenamento local, o jogo deve funcionar apenas quando houver conexão com o servidor (mostrar erro "bonito").
- Apesar de o idioma das perguntas ser em inglês (consultado em tempo-real do Open Trivia DB), o restante da aplicação deve ser internacionalizável (padrão: inglês).
- Caprichar nos layouts.
- Utilizar ConstraintLayout.
- Utilizar Fragments, Bottom Navigation (ou outro) e Navigation Framework.
- Não trave a aplicação do usuário.
- Aplicar boas práticas de Android e programação: indentação, formatação, convenções de escrita de código (CameCase, etc), estrutura de pacotes organizada, código limpo, ...
- Analise bem as requisições e os JSON de resposta de cada API usando o Postman.


## Desafio (bônus, caso tenha cumprido os demais requisitos)
- Procure uma API de tradução e traduza as perguntas, respostas e categorias em tempo real para o idioma do usuário.


## Explicação da API
Aplicação executando em https://super-trivia-server.herokuapp.com

Código-fonte: https://github.com/seccomiro/trivia-server

Todos os endpoints (URLs) podem retornar um 200 OK (sucesso) ou algum código de erro (por exemplo, 401 Unauthorized) que devem ser verificados no retorno da chamada. O JSON resultante também pode mudar.

Todos os endpoints retornam JSON no formato JSend para facilitar a leitura, tanto nas falhas, quanto nos sucessos.


Registrar usuário
POST https://super-trivia-server.herokuapp.com/users

Deve ser enviado um JSON no corpo da requisição, contendo o seguinte formato:

> { "email": "usuario@email.com", "name": "Nome do jogador", "password": "321321321"}
O retorno de sucesso é o seguinte:

> { "status": "success", "data": { "user": { "email": "usuario@email.com", "name": "Nome do jogador", "token": "eyJhbGciOiJIUzI1NiJ9.eyazdWIiOjExLCJleHAiOjE2MDgzNTIzMjh9.ujgtkbcPESxPSrUszxEH81vK-ZhdigTnTGu67EhhUcw" } }}


Login
POST https://super-trivia-server.herokuapp.com/auth
O JSON de requisição e resposta semelhantes ao registro, só não precisa passar o atributo name.

Lista de categorias
GET https://super-trivia-server.herokuapp.com/categories


Ranking
GET https://super-trivia-server.herokuapp.com/ranking



Todos os endpoints daqui em diante necessitam de autenticação utilizando o token obtido no Registro/Login.

Para que a autenticação ocorra, o token deve ser enviado através de um Header de requisição (@Headers do Retrofit) exatamente neste formato: Authorization: <token>. Exemplo:

Authorization: eyJhbGciOiJIUzI1NiJ9.eyazdWIiOjExLCJleHAiOjE2MDgzNTIzMjh9.ujgtkbcPESxPSrUszxEH81vK-ZhdigTnTGu67EhhUcw


Iniciar um novo jogo ou retorna o jogo corrente do usuário autenticado
GET https://super-trivia-server.herokuapp.com/games?difficulty=hard&category_id=1

O parâmetro difficulty é opcional, mas deve seguir os valores: hard, medium ou easy.

O parâmetro category_id é opcional, mas deve usar um dos IDs de categoria válidos carregados do endpoint de categorias.

Este endpoint também serve para consultar a pontuação atualizada do jogo a qualquer momento.



Finalizar o jogo corrente (erro caso não haja)
DELETE https://super-trivia-server.herokuapp.com/games



Solicitar uma nova questão para responder (podem ocorrer erros)
GET https://super-trivia-server.herokuapp.com/problems/next

Retorna uma questão gerada aleatoriamente pela API.

 {
    "status": "success",
    "data": {
        "problem": {
            "question": "What is the most commonly used noun in the English language?",
            "difficulty": "hard",
            "category": {
                "id": 1,
                "name": "General Knowledge"
            },
            "answers": [
                {
                    "order": 1,
                    "description": "Water"
                },
                {
                    "order": 2,
                    "description": "Home"
                },
                {
                    "order": 3,
                    "description": "Time"
                },
                {
                    "order": 4,
                    "description": "Man"
                }
            ]
        }
    }
}


Ver a questão atualmente aberta para resposta (podem ocorrer erros)
GET https://super-trivia-server.herokuapp.com/problems/view

Endpoint muito parecido com o anterior, mas não busca por uma nova questão e sim mostra a questão atualmente esperando resposta.



Responder à questão aberta (podem ocorrer erros)
POST https://super-trivia-server.herokuapp.com/problems/answer?answer=4
O parâmetro ?answer=4 é usado para identificar qual resposta o usuário está dando (1, 2, 3 ou 4), de acordo com a order das answers do JSON anterior.
A resposta mostra a pontuação atualizada:

{

    "status": "success",

    "data": {

        "answer": {

            "status": "incorrect",

            "correct_answer": {

                "order": 3,

                "description": "Time"

            },

            "score": -10

        }

    }

}
