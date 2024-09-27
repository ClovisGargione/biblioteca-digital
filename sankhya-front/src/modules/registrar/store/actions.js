import http, { services } from '@/http'

// eslint-disable-next-line
export  const  ActionDoRegister = async ({ dispatch }, payload) => {
    services.registrar.post.data = payload
    return http(services.registrar.post)
}