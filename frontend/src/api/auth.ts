import axios from 'axios'

export function loginApi(username: string, password: string) {
  return axios.post('/api/auth/login', { username, password })
}

export function registerApi(username: string, password: string) {
  return axios.post('/api/auth/register', { username, password })
} 