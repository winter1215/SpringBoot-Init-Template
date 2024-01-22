import type { RequestOptions } from '@@/plugin-request/request';
import type { RequestConfig } from '@umijs/max';
import { message } from 'antd';
import { BACKEND_HOST } from './constant';
import e from 'express';
// cross-env 可以跨平台设置环境变量
const isDev = process.env.NODE_ENV === 'development';

// 与后端约定的响应数据格式
interface ResponseStructure {
  code?: number;
  data: any;
  message: string;
}

// 请求不带token的白名单
const whiteList = [
  '/user/login',
  '/user/login',
  '/user/wx/handshake',
  '/user/wx/loginInfo',
  '/user/wx/checkLogin/**',
  '/user/register',
  '/user/forget',
  '/public/**',
];

export const config: RequestConfig = {
  // baseURL: isDev ? BACKEND_HOST_LOCAL : BACKEND_HOST_PROD,
  // todo: 测试
  baseURL: BACKEND_HOST,

  // 请求拦截器
  requestInterceptors: [
    (config: RequestOptions) => {
      // 不在白名单中的请求，都需要带上token
      const token = localStorage.getItem('token') ?? '';
      if (config.headers && !whiteList.includes(config.url ?? '')) {
        config.headers.token = token;
      }
      return { ...config };
    },
  ],

  // 响应拦截器
  responseInterceptors: [
    (response) => {
      // 拦截响应数据，进行个性化处理
      const { data: responseData } = response;
      const data = responseData as unknown as ResponseStructure;
      const { code } = data;
      const requestPath: string = response.config.url ?? '';

      if (
        code === 40100 &&
        !requestPath.includes('user/login') &&
        !location.pathname.includes('/user/login')
      ) {
        // 跳转至登录页
        window.location.href = `/user/login?redirect=${window.location.href}`;
      }

      if (code !== 0) {
        throw new Error(data.message);
      }
      return response;
    },
  ],
};
