import http, { services } from '@/http'
import * as types from './mutation-types'

export  const  ActionListarLivros = async ({ dispatch }) => {
   return http(services.livro.get).then(res => {
      const livros = res.data
      dispatch('ActionSetLivros', livros)
    })
}

// eslint-disable-next-line
export  const  ActionAdicionarLivro = async ({ dispatch }, payload) => {
    services.livro.post.data = payload
    return http(services.livro.post)
}

// eslint-disable-next-line
export  const  ActionAtualizarLivro = async ({ dispatch }, payload) => {
    services.livro.put.data = payload
    return http(services.livro.put)
}

// eslint-disable-next-line
export  const  ActionRemoverLivro = async ({ dispatch }, payload) => {
    services.livro.delete.data = payload
    return http(services.livro.delete)
}

export  const  ActionTemLivro = async ({ dispatch }) => {
    return http(services.livro.temLivro).then(res => {
        const temLivro = res.data
        dispatch('ActionSetTemLivro', temLivro)
    })
}

export const ActionSetLivros = ({ commit }, payload) => {
    commit(types.SET_LIVROS, payload)
}  

export const ActionSetTemLivro = ({ commit }, payload) => {
    commit(types.SET_TEM_LIVROS, payload)
}  