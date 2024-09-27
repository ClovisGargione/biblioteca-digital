import http, { services } from '@/http'
import * as storage from '../storage'
import * as types from './mutation-types'

export  const  ActionDoLogin = async ({ dispatch }, payload) => {
  services.auth.login.data = payload
  return http(services.auth.login).then(res => {
    const { token } = res.data
    dispatch('ActionSetToken', token)
  })
}

export const ActionGetUser = ({ dispatch}) => {
  return http(services.auth.user).then(async res => {
    const user = res.data
    dispatch('ActionSetUser', user)
  })
}

export const ActionSetUser = ({ commit }, payload) => {
  storage.setUser(JSON.stringify(payload))
  commit(types.SET_USER, payload)
}

export const ActionSetToken = ({ commit }, payload) => {
  storage.setLocalToken(payload)
  storage.setHeaderToken(payload)
  commit(types.SET_TOKEN, payload)
}

export const ActionSignOut = ({ dispatch }) => {
  storage.setHeaderToken('')
  storage.deleteLocalToken()
  dispatch('ActionSetUser', {})
  dispatch('ActionSetToken', '')
}

export const ActionCheckToken = ({ dispatch, state }) => {
  if (state.token) {
    return Promise.resolve(state.token)
  }

  const token = storage.getLocalToken()

  if (!token) {
    dispatch('ActionSignOut')
    return Promise.reject('Token não informado')
  }

  const user = storage.getUser()
  if (user) {
    dispatch('ActionSetUser', JSON.parse(user))
  }
  dispatch('ActionSetToken', token)
  return Promise.resolve(token)
}