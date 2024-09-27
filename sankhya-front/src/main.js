import './assets/main.css'
import 'bootstrap/dist/css/bootstrap.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import {store} from './store'
import 'bootstrap'
import {LoadingPlugin} from 'vue-loading-overlay';
import 'vue-loading-overlay/dist/css/index.css';
import loaderProps from './assets/loader-config'

const app = createApp(App)
app.use(router)
app.use(store)
app.use(LoadingPlugin, loaderProps)
app.mount('#app')
