import { routes as auth } from '../modules/auth'
import { routes as home } from '../views/home'
import { routes as registrar } from '../modules/registrar'
import { routes as livro } from '../modules/livro'

export default [
  ...auth,
  ...home,
  ...registrar,
  ...livro
]