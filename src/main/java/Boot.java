import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;

public class Boot {

	public static void main(String[] args) throws Exception {
		HandlerCollection handlers = new HandlerCollection();
		RequestLogHandler requestLogHandler = new RequestLogHandler();
		handlers.setHandlers(new Handler[] { requestLogHandler });

		Server server = new Server(0);
		server.setHandler(handlers);
		server.start();

		String pid = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
		int port = server.getConnectors()[0].getLocalPort();
		System.out.println("pid = " + pid + ", port = " + port);

		NCSARequestLog requestLog = new NCSARequestLog(pid + "-" + port + ".log");
		requestLogHandler.setRequestLog(requestLog);

		server.join();
	}
}