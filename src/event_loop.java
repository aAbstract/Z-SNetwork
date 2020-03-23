package ais.zero.zsnet;

import java.lang.Thread;
import java.io.InputStream;

class event_loop extends Thread {
  // Fields
  // Serial Input Stream Object
  private InputStream in;
  // On Read Event Handler
  public read_event_handler handler;
  // System's Los Manager
  private log main_log;
  // Parent Caller Object
  private zsnet parent;
  // Constructor
  public event_loop(InputStream _in, zsnet _parent, read_event_handler _hander, log _main_log) {
    this.in = _in;
    this.handler = _hander;
    this.main_log = _main_log;
    this.parent = _parent;
  }
  // Async Task
  public void run() {
    while (true) {
      try {
        if (this.in.available() > 0) {
          String data = "";
          int c = 0;
          c = this.in.read();
          while (c != 10) {
            if (c == -1) {
              this.main_log.verbose(log.LOG_INFO, "Client Disconnected");
              this.parent.accept_client();
              return;
            }
            data += Character.toString((char)c);
            c = this.in.read();
          }
          this.handler.execute(data);
          Thread.sleep(100);
        }
      } catch (Exception e) {
        this.main_log.verbose(log.LOG_ERROR, e.getMessage());
      }
    }
  }
}
