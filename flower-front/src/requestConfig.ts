import type { RequestOptions } from '@@/plugin-request/request';
import type { RequestConfig } from '@umijs/max';
import { message } from 'antd';
import { BACKEND_HOST_LOCAL, BACKEND_HOST_PROD } from './constant';
// cross-env 可以跨平台设置环境变量
const isDev = process.env.NODE_ENV === 'development';


// 与后端约定的响应数据格式
interface ResponseStructure {
  code?: number;
  data: any;
  message: string;
}

/**
 * @name 错误处理
 * pro 自带的错误处理， 可以在这里做自己的改动
 * @doc https://umijs.org/docs/max/request#配置
 */
export const config: RequestConfig = {
  baseURL: isDev ? BACKEND_HOST_LOCAL : BACKEND_HOST_PROD,

  // 请求拦截器
  requestInterceptors: [
    (config: RequestOptions) => {
      // 拦截请求配置，进行个性化处理。
      return { ...config };
    },
  ],

  // 响应拦截器
  responseInterceptors: [
    (response) => {
      // 拦截响应数据，进行个性化处理
      const { data: responseData } = response;
      const data = responseData as unknown as ResponseStructure
      if (data?.code !== 0) {
        message.error('请求失败' + data.message);
      }
      return response;
    },
  ],
};
