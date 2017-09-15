package com.test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ScriptSessions;
import org.directwebremoting.WebContextFactory;

public class Message {

	public void addMessage(String userid, String message) {
		final String userId = userid;
		final String autoMessage = message;
		System.out.println("To:" + userid + ",Msg:" + message);
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			public boolean match(ScriptSession session) {
				if (session.getAttribute("name") == null)
					return false;
				else
					return (session.getAttribute("name")).equals(userId);
				}
			}, new Runnable() {
				private ScriptBuffer script = new ScriptBuffer();
	
				public void run() {
					script.appendCall("receiveMessages", autoMessage);
					Collection<ScriptSession> sessions = Browser
							.getTargetSessions();
					for (ScriptSession scriptSession : sessions) {
						if(scriptSession.getAttribute("name").equals(userId)){
							scriptSession.addScript(script);
							break;
						}
					}
				}
			});
	}

	public void onPageLoad(String name) {
		HttpSession session = WebContextFactory.get()
				.getSession();
		session.setAttribute("name", name);
		WebContextFactory.get().getScriptSession().setAttribute("name", name);

	}

}