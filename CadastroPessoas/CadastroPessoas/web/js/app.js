
var app = angular.module("CadastroPessoasApp", []);
app.controller("PessoasController", function ($scope, PessoasService) {
    list();
    $scope.pessoa = {};
    function list() {
        PessoasService.listar().then(function (resposta) {
            $scope.pessoas = resposta.data;
            $scope.menssagenEdit = "";
        });
    }
    ;
    $scope.salvar = function (pessoa) {
        if (pessoa.nome.length > 5 && pessoa.cpf.length > 10 &&pessoa.email.length > 8 ) {
            PessoasService.salvar(pessoa).then(list);
        } else {
            alert("Insira dados validos!");
        }
        $scope.pessoa = {};
        $scope.menssagenEdit = "";
    };

    $scope.excluir = function (pessoa) {
        PessoasService.excluir(pessoa, $scope.pessoas).then(list);
        $scope.menssagenEdit = "";
    };
    $scope.editar = function (pessoa) {
        var msg = "Ao editar pressione Enter para confirmar!";
        $scope.menssagenEdit = msg;
        $scope.pessoa = PessoasService.editar(pessoa);


    };

    $scope.cancelar = function () {
        $scope.pessoa = PessoasService.cancelar();
        $scope.menssagenEdit = "";
    };
});
app.service("PessoasService", function ($http) {

    var api = "http://localhost:8084/CadastroPessoas/webresources/servicos/";
    this.listar = function () {
        return $http.get(api + "listar");
    };

    this.salvar = function (pessoa) {
        var data = pessoa;

        if (pessoa.id) {
            return $http({
                method: 'PUT',
                url: api + "atualizar",
                data: data
            });


        } else {
            return $http({
                method: 'POST',
                url: api + "adicionar",
                data: data
            });
        }

    };

    this.excluir = function (pessoa, pessoas) {
        for (var i = 0; i < pessoas.length; i++) {

            if (pessoas[i].id == pessoa.id) {
                pessoas.splice(i, 1);
                return $http({
                    method: 'DELETE',
                    url: api + "deletar",
                    data: pessoa.id
                });
            }
        }
    };
    this.editar = function (pessoa) {
        pessoa = angular.copy(pessoa);
        return pessoa;

    };

    this.cancelar = function () {
        return {};
    };
});



function ValidaCPF() {
    var RegraValida = document.getElementById("cpf").value;
    var cpfValido = /^(([0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2})|([0-9]{11}))$/;
    if (cpfValido.test(RegraValida) == true) {
        return cpfValido;
    } else {
        cpfValido = null;
        return cpfValido;
        alert("CPF Inválido");
    }
}
function fMasc(objeto, mascara) {
    obj = objeto
    masc = mascara
    setTimeout("fMascEx()", 1)
}

function fMascEx() {
    obj.value = masc(obj.value)
}

function mCPF(cpf) {
    cpf = cpf.replace(/\D/g, "")
    cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2")
    cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2")
    cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2")
    return cpf
}