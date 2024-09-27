import {store} from '../store'
import * as storage from '../modules/auth/storage'

export default async (to, from, next) => {
  if (to.name && to.name !== 'login' && to.name !== 'registrar' && !store.getters['auth/hasToken']) {
    try {
      await store.dispatch('auth/ActionCheckToken')
      next()
    } catch (err) {
      return next( { name: 'login' } )
      
    }
  } else {
    if (to.name && to.name === 'login' && storage.getLocalToken()) {
      return next({ name: 'home' })
    }
    if (!to.name && !storage.getLocalToken()) {
        return next({ name: 'login' })
    } else {
      next()
    }
  }
}