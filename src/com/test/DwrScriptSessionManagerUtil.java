package com.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

public class DwrScriptSessionManagerUtil extends DwrServlet {

	private static final long serialVersionUID = -7504612622407420071L;
	private static ScriptSessionListener listener;
	private static Container container = ServerContextFactory.get().getContainer();
	private static ScriptSessionManager manager = container
			.getBean(ScriptSessionManager.class);
	static{
		
	}
	public void init() throws ServletException {
		getScriptSessionListener();
	}
	
	private static ScriptSessionListener getScriptSessionListener(){
		if(listener == null){
			listener = new ScriptSessionListener() {
				public void sessionCreated(ScriptSessionEvent ev) {
					HttpSession session = WebContextFactory.get().getSession();
					String name = session.getAttribute("name").toString();
					System.out.println("a ScriptSession is created!" + session.getAttribute("name"));
					ev.getSession().setAttribute("name", name);
				}
				public void sessionDestroyed(ScriptSessionEvent ev) {
					System.out.println("a ScriptSession is distroyed" + ev.getSession().getAttribute("name"));
				}
			};
			manager.addScriptSessionListener(listener);
		}
		return listener;
	}

}