import { createRouter, createWebHistory } from 'vue-router'
import routes from './routes'
import beforeEach from './beforeEach'

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach(beforeEach)

export default router