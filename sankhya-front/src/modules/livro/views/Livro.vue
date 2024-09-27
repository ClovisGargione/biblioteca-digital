  <template>
      <div class="container">
      <div class="mt-4 p-5 bg-green text-white rounded">
        <h1>Biblioteca digital</h1>
        <p>Esse é o sistema onde você registra os livros que já leu!!!</p>
      </div>
      <br>
      <h3>
        Biblioteca de {{ user.nome }}
      </h3>
      <hr />
      <div class="row">
        <div class="col-6">
          <div class="container">
            <h4>Controle de livros</h4>
            <form @submit.prevent="form.id ? atualizar() : adicionar()">
              <div class="row">
                <div class="form-group col-6">
                  <label for="titulo">Título:</label> <input @blur="validarCampo(form.titulo, 'tituloErr')" class="form-control"
                    id="titulo" type="text" v-model="form.titulo" />
                  <small ref="tituloErr" class="text-danger" :style="{ display: 'none'}">preencher o titulo</small>
                </div>
              </div>
              <div class="row">
                <div class="form-group col-6">
                  <label for="autor">Autor:</label> <input @blur="validarCampo(form.autor, 'autorErr')" class="form-control"
                    id="autor" type="text" v-model="form.autor" />
                  <small ref="autorErr" class="text-danger" :style="{ display: 'none'}">preencher o autor</small>
                </div>
              </div>
              <div class="row">
                <div class="form-group col-6">
                  <label for="nota">Nota:</label> <input @blur="validarCampo(form.nota, 'notaErr')" class="form-control" min="0" max="10"
                    id="nota" type="number" v-model="form.nota" />
                  <small ref="notaErr" class="text-danger" :style="{ display: 'none'}">Dê uma nota até 10 para o livro</small>
                </div>
              </div>
              <br />
              <div class="row">
                <div class="form-group col-6">
                  <div class="row">
                    <div class="col">
                      <button class="btn btn-green" type="submit" :disabled="!isEmptyForm()">
                          <span v-if="!form.id">Adicionar livro</span>
                          <span v-if="form.id">Atualizar livro</span>
                      </button>
                    </div>
                    <div class="col">
                      <button class="btn btn-light" type="button" @click="limparForm()" :disabled="!isEmptyForm()">
                          <span>Limpar</span>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div class="col-6">
          <div class="container">
            <div v-if="temLivro">
              <h4>Esses são seus livros cadastrados na aplicação Biblioteca digital
              </h4>
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th>Título</th>
                    <th>Autor</th>
                    <th>Nota</th>
                    <th>Ação</th>
                  </tr>
                </thead>
                <tbody :key="livro.id" v-for="livro in livros">
                  <tr class="cursor-pointer">
                    <td @click="setForm(livro)">{{livro.titulo}}</td>
                    <td @click="setForm(livro)">{{ livro.autor }}</td>
                    <td @click="setForm(livro)">{{livro.nota}}</td>
                    <td @click="remover(livro)"><a class="link-danger link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Remover</a></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div v-if="!temLivro">
              <div class="alert alert-info" role="alert">
                <span class="glyphicon glyphicon-exclamation-sign"
                  aria-hidden="true"></span> <span class="sr-only"></span>
                Você ainda não tem livros cadastrados!
              </div>
            </div>
            <router-link to="/home"><button class="btn btn-secondary" type="button">Voltar</button>
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </template>

  <script>
  import { mapActions, mapState } from 'vuex'
  export default {
    data: () => ({
      form: {
        id: '',
        titulo: '',
        autor: '',
        nota: null
      }
    }),
    computed: {
      ...mapState('livro', ['livros', 'temLivro']),
      ...mapState('auth', ['user'])
    },
    mounted () {
      this.init()
    },
    methods: {
      ...mapActions('livro', ['ActionListarLivros', 'ActionAdicionarLivro', 'ActionAtualizarLivro', 'ActionRemoverLivro', 'ActionTemLivro']),
      async adicionar() {
        const loader = this.$loading.show()
          try {
          await this.ActionAdicionarLivro(this.form)
          await this.ActionTemLivro()
          await this.ActionListarLivros()
          this.limparForm()
          loader.hide()
        } catch (err) {
          loader.hide()
          alert(err ? err.data : 'Não foi possível adicionar o livro ' + this.form.titulo)
        }
      },
      async init(){
        const loader = this.$loading.show()
          try {
            await this.ActionTemLivro()
            await this.ActionListarLivros()
            loader.hide()
          } catch (err) {
            loader.hide()
              alert(err ? err.data : 'Não foi possível listar os livros')
          }
      },
      async atualizar() {
        const loader = this.$loading.show()
        try {
            await this.ActionAtualizarLivro(this.form)
            await this.ActionTemLivro()
            await this.ActionListarLivros()
            this.limparForm()
            loader.hide()
        } catch (err) {
          loader.hide()
          alert(err ? err.data : 'Não foi possível atualizar o livro ' + this.form.titulo)
        }
      },
      async remover(livro) {
        const loader = this.$loading.show()
        try {
            await this.ActionRemoverLivro(livro)
            await this.ActionListarLivros()
            await this.ActionTemLivro()
            loader.hide()
        } catch (err) {
          loader.hide()
          alert(err ? err.data : 'Não foi possível remover o livro ' + this.form.titulo)
        }
      },
      isEmptyForm() {
        return this.form.autor && this.form.titulo && this.form.nota
      },
      validarCampo(obj, name) {
        this.$refs[name].style.display = !obj || !obj.toString().length ? "block" : "none";
      },
      setForm(form){
        this.form = form
      },
      limparForm() {
        this.form = {}
      }
    }
  }
  </script>

  <style lang="scss" scoped>
  .bg-green {
    background-color: #66cb66;
  }

  .btn-green {
    background-color: #66cb66 !important;
    color: #fff;
  }

.cursor-pointer{
  cursor: pointer;
}
  </style>