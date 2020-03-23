package ais.zero.zsnet;

// System Imports
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;

public class zsnet {
  // Private Fields
  // Logging Object
  private log logger;
  // Server Socket Object
  private ServerSocket ss;
  // Client Socket Object
  private Socket cs;
  // Background Porcess Object
  private read_event_handler handler;
  private event_loop async_process;
  // Utils
  // Accept New Client
  public void accept_client() {
    try {
      this.logger.verbose(log.LOG_INFO, "Waiting For Clients...");
      this.cs = this.ss.accept();
      this.logger.verbose(log.LOG_INFO, this.cs.getRemoteSocketAddress().toString() + " Connected");
      this.logger.verbose(log.LOG_INFO, "Starting Async Event Loop...");
      this.async_process = new event_loop(this.cs.getInputStream(), this, this.handler, this.logger);
      this.async_process.start();
      this.logger.verbose(log.LOG_INFO, "Async Event Loop Started");
    } catch (Exception e) {
      this.logger.verbose(log.LOG_ERROR, e.getMessage());
    }
  }
  // Default Constructor
  // @param port - server port
  // @param _debug - debug flag for system logs
  public zsnet(int port, boolean _debug, read_event_handler _handler) {
    this.logger = new log(_debug);
    try {
      this.ss = new ServerSocket(port);
      this.logger.verbose(log.LOG_INFO, "Server Started at Port: " + port);
      this.handler = _handler;
      this.accept_client();
    } catch (Exception e) {
      this.logger.verbose(log.LOG_ERROR, e.getMessage());
    }
  }
  // Properties
  // Debug Mode Getter and Setter
  boolean get_debug() {return this.logger.debug; }
  void set_debug(boolean new_mode) {
    this.logger.debug = new_mode;
  }
  // Handler Callback Setter
  void set_read_handler(read_event_handler new_handler) {
    this.async_process.handler = new_handler;
  }
}
