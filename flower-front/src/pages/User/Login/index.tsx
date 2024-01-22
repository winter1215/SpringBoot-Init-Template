import Footer from '@/components/Footer';
import { BACKEND_HOST } from '@/constant';
import {
  checkWxLoginUsingGet,
  getWxLoginInfoUsingGet,
  userLoginUsingPost,
} from '@/services/backend/userController';
import {
  AlipayCircleOutlined,
  LockOutlined,
  TaobaoCircleOutlined,
  UserOutlined,
  WeiboCircleOutlined,
} from '@ant-design/icons';
import { LoginForm, ProFormCheckbox, ProFormText } from '@ant-design/pro-components';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import { Helmet, history } from '@umijs/max';
import { Alert, Card, message, Spin, Tabs } from 'antd';
import React, { useEffect, useRef, useState } from 'react';
import Settings from '../../../../config/defaultSettings';

const ActionIcons = () => {
  const langClassName = useEmotionCss(({ token }) => {
    return {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    };
  });
  return (
    <>
      <AlipayCircleOutlined key="AlipayCircleOutlined" className={langClassName} />
      <TaobaoCircleOutlined key="TaobaoCircleOutlined" className={langClassName} />
      <WeiboCircleOutlined key="WeiboCircleOutlined" className={langClassName} />
    </>
  );
};

const LoginMessage: React.FC<{
  content: string;
}> = ({ content }) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};

const Login: React.FC = () => {
  const [wxLoading, setWxLoading] = useState<boolean>(false);
  const [type, setType] = useState<string>('account');
  const [wxLoginInfo, setWxLoginInfo] = useState<API.WxLoginInfoVo>();
  // 用 ref 来保存定时器
  const intervalId = useRef<number>();

  const containerClassName = useEmotionCss(() => {
    return {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    };
  });

  useEffect(() => {
    // 如果已经登录，跳转到首页(发送请求到后端验证 token 是否有效)
    if (localStorage.getItem('token')) {
      history.push('/');
    }
  }, []);

  useEffect(() => {
    if (type === 'wechat') {
      initWxQr();
    }

    return () => {
      // 关闭定时器
      clearInterval(intervalId.current);
    };
  }, [type]);

  // 初始化微信二维码信息
  const initWxQr = async () => {
    try {
      setWxLoading(true);
      const res = await getWxLoginInfoUsingGet();
      setWxLoginInfo(res.data);
      setWxLoading(false);
      // 在setWxLoginInfo的回调函数中设置定时器
      setWxLoginInfo((prevWxLoginInfo) => {
        intervalId.current = window.setInterval(async () => {
          // 轮询是否输入验证码
          const res = await checkWxLoginUsingGet({ ticket: prevWxLoginInfo?.ticket || '' });
          if (res.data) {
            clearInterval(intervalId.current);
            message.success('登录成功！');
            localStorage.setItem('token', res.data.token ?? '');
            localStorage.setItem('userInfo', JSON.stringify(res.data));
            history.push('/');
          } 
          
        }, 1500);
        return prevWxLoginInfo;
      });
    } catch (error) {
      message.error('获取微信二维码失败！');
    }
  };


  const handleSubmit = async (values: API.UserLoginRequest) => {
    try {
      // 登录
      const res = await userLoginUsingPost({
        ...values,
      });
      localStorage.setItem('token', res.data?.token ?? '');
      localStorage.setItem('userInfo', JSON.stringify(res.data));
      message.success('登录成功！');
      const urlParams = new URL(window.location.href).searchParams;
      history.push(urlParams.get('redirect') || '/');
      return;
    } catch (error) {
      message.error('登录失败，请重试！');
    }
  };

  return (
    <div className={containerClassName}>
      <Helmet>
        <title>
          {'登录'}- {Settings.title}
        </title>
      </Helmet>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src="/logo.svg" />}
          title="Ant Design"
          subTitle={'Ant Design 是西湖区最具影响力的 Web 设计规范'}
          initialValues={{
            autoLogin: true,
          }}
          actions={['其他登录方式 :', <ActionIcons key="icons" />]}
          onFinish={async (values) => {
            await handleSubmit(values as API.UserLoginRequest);
          }}
        >
          <Tabs
            activeKey={type}
            onChange={setType}
            centered
            items={[
              {
                key: 'account',
                label: '账户密码登录',
              },
              {
                key: 'wechat',
                label: '微信扫码登录',
              },
            ]}
          />
          {/* 
          {status === 'error' && loginType === 'account' && (
            <LoginMessage content={'错误的用户名和密码(admin/ant.design)'} />
          )} */}
          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined />,
                }}
                placeholder={'请输入用户名'}
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined />,
                }}
                placeholder={'请输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                ]}
              />
            </>
          )}

          {type === 'wechat' && (
            <>
              <div style={{ display: 'flex', justifyContent: 'center', marginBottom: 20 }}>
                <Card
                  style={{
                    height: 350,
                    width: 350,
                    borderRadius: 16,
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                  }}
                >
                  {/* 块级元素高度默认是元素的高度 */}
                  {wxLoading ? (
                    <Spin />
                  ) : (
                    <div>
                      <img
                        style={{ height: 250, width: 250 }}
                        src={BACKEND_HOST + wxLoginInfo?.url}
                      />
                      <div style={{ textAlign: 'center' }}>验证码: {wxLoginInfo?.verifyCode}</div>
                    </div>
                  )}
                </Card>
              </div>
            </>
          )}
          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              自动登录
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
            >
              忘记密码 ?
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};
export default Login;
