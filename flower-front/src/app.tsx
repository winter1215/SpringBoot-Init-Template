import Footer from '@/components/Footer';
import { Question } from '@/components/RightContent';
import { LinkOutlined } from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import { SettingDrawer } from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import { AvatarDropdown, AvatarName } from './components/RightContent/AvatarDropdown';
import { config } from './requestConfig';
const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';

/**
 * 每次刷新页面时，都会执行一次
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */


export async function getInitialState(): Promise<{
  id: string,
  username: string
}> {
  const currentUser: { id: string, username: string } = { id: '', username: '' };
    // 如果不是登录页面，执行
  const { location } = history;
  if (location.pathname !== loginPath) {
    try {
      // const res = await getLoginUser();
      // initialState.currentUser = res.data;
    } catch (error: any) {
      // 如果未登录
    }
  }
  return currentUser;
}


// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState }) => {
  return {
    avatarProps: {
      render: () => {
        return <AvatarDropdown />;
      },
    },
    waterMarkProps: {
      content: initialState?.username,
    },
    footerRender: () => <Footer />,
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    ...defaultSettings as Partial<LayoutSettings>,

  };
};


/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request = {
  ...config,
};
