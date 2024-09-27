<template>
  <div class="container pt-3">
    <div class="mt-4 p-5 bg-green text-white rounded">
      <h1>Biblioteca digital</h1>
      <p>Todos os seus livros em um só lugar!!!</p>
    </div>
    <br/>
    <div class="row">
      <div class="col">
        <h3>Olá, {{ user.nome }}!</h3>
        <p>
          Você está acessando sua biblioteca digital
        </p>
        <p>
            <button class="btn btn-secondary" @click="logout()">Logout</button>

        </p>
      </div>
      <div class="col">
        <h3>Veja os livros que você já cadastrou</h3>
        <p>Aqui você pode ver os livros que já cadastrou ou adicionar
          novos livros.
        </p>
        <p>
          <router-link to="/livro">
            <button class="btn btn-green">Controle de
              livros</button>
          </router-link>
        </p>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  data: () => ({

  }),
  computed: {
    ...mapState('auth', ['user'])
  },
  methods: {
    ...mapActions('auth', ['ActionSignOut']),
    async logout() {
      await this.ActionSignOut()
      this.$router.push({ name: 'login' })
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
</style>
