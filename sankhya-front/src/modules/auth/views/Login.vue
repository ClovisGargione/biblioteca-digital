<template>
  <div class="sidenav">
    <div class="login-main-text">
      <h2>Biblioteca digital<br> Página de login</h2>
      <p>Entre ou cadastre-se para ter acesso.</p>
    </div>
  </div>
  <div class="main">
    <div class="d-flex align-items-center justify-content-center h-100">
      <div class="card">
        <div class="card-body">
          <h5 class="card-title">Login</h5>
          <form @submit.prevent="submit()">
            <div class="mb-3">
              <input type="text" class="form-control" v-model="form.login" placeholder="Login">
            </div>
            <div class="mb-3">
              <input type="password" class="form-control" v-model="form.senha" placeholder="Senha">
            </div>
            <div class="row">
              <div class="col">
                <button type="submit" class="btn btn-green w-100" :disabled="!isEmptyForm()">Entrar</button>
              </div>
              <div class="col">
                <router-link to="/registrar"><button type="submit"
                    class="btn btn-secondary w-100">Regitrar</button></router-link>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  data: () => ({
    form: {
      login: '',
      senha: ''
    }
  }),
  methods: {
    ...mapActions('auth', ['ActionDoLogin', 'ActionGetUser']),
    async submit() {
      const loader = this.$loading.show()
      try {
        await this.ActionDoLogin(this.form)
        await this.ActionGetUser()
        this.$router.push({ name: 'home' })
        loader.hide()
      } catch (err) {
        loader.hide()
        alert(err ? err : 'Não foi possível fazer login')
      }
    },
    isEmptyForm() {
      return this.form.login && this.form.senha
    }
  }
}
</script>

<style scoped>
body {
  font-family: "Lato", sans-serif;
}

.main-head {
  height: 150px;
  background: #FFF;

}

.sidenav {
  height: 100%;
  background-color: #66cb66;
  overflow-x: hidden;
  padding-top: 20px;
}

.main {
  padding: 0px 10px;
}

@media screen and (max-height: 450px) {
  .sidenav {
    padding-top: 15px;
  }
}

@media screen and (max-width: 450px) {
  .login-form {
    margin-top: 10%;
  }

  .register-form {
    margin-top: 10%;
  }
}

@media screen and (min-width: 768px) {
  .main {
    height: 100%;
    margin-left: 40%;
    position: absolute;
    width: 60%;
  }

  .sidenav {
    width: 40%;
    position: fixed;
    z-index: 1;
    top: 0;
    left: 0;
  }

  .login-form {
    margin-top: 80%;
  }

  .register-form {
    margin-top: 20%;
  }
}

.login-main-text {
  margin-top: 20%;
  padding: 60px;
  color: #fff;
}

.login-main-text h2 {
  font-weight: 300;
}

.btn-green {
  background-color: #66cb66 !important;
  color: #fff;
}

.login-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;

}

.login-page>.card {
  width: 30%;
}
</style>