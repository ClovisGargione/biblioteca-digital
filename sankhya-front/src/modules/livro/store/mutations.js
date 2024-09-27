import * as types from './mutation-types'

export default {
  [types.SET_LIVROS] (state, payload) {
    state.livros = payload
  },
  [types.SET_TEM_LIVROS] (state, payload) {
    state.temLivro = payload
  }
}