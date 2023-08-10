package com.ysgsignin.wxinfo.common;

import java.io.Serializable;

/**
 * @author: 叶双贵
 * @version: 1.0
 * @编辑时间：2023/7/26 12:03
 **/
public class BaseResponse implements Serializable {
    public static int RESP_CODE_OK = 200;
    protected boolean success;
    protected int code;
    protected String msg;
    protected String redirectUrl;


    public BaseResponse(boolean success) {
        this.success = success;
    }

    public BaseResponse(boolean success,String msg) {
        this.success = success;
        this.msg = msg;

    }

    public BaseResponse(boolean success, int code, String msg, String redirectUrl) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.msg = redirectUrl;
    }


    public boolean isSuccess() {
        return this.success;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseResponse)) {
            return false;
        } else {
            BaseResponse other = (BaseResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isSuccess() != other.isSuccess()) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                label40: {
                    Object this$msg = this.getMsg();
                    Object other$msg = other.getMsg();
                    if (this$msg == null) {
                        if (other$msg == null) {
                            break label40;
                        }
                    } else if (this$msg.equals(other$msg)) {
                        break label40;
                    }

                    return false;
                }

                Object this$redirectUrl = this.getRedirectUrl();
                Object other$redirectUrl = other.getRedirectUrl();
                if (this$redirectUrl == null) {
                    if (other$redirectUrl != null) {
                        return false;
                    }
                } else if (!this$redirectUrl.equals(other$redirectUrl)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseResponse;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + (this.isSuccess() ? 79 : 97);
        result = result * 59 + this.getCode();
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $redirectUrl = this.getRedirectUrl();
        result = result * 59 + ($redirectUrl == null ? 43 : $redirectUrl.hashCode());
        return result;
    }

    public String toString() {
        return "BaseResponse(success=" + this.isSuccess() + ", code=" + this.getCode() + ", msg=" + this.getMsg() + ", redirectUrl=" + this.getRedirectUrl() + ")";
    }
}
