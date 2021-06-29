package indi.netty.yufr.chat.attribute;

import indi.netty.yufr.chat.session.Session;
import io.netty.util.AttributeKey;


public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
