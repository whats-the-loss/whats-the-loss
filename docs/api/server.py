import http.server
import os

PORT = 8000


class NoCacheHTTPRequestHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        # Redirect root path to either 'index.html' or 'redoc-static.html'
        if self.path == '/':
            if os.path.exists(self.translate_path('index.html')):
                self.path = '/index.html'
            else:
                self.path = '/redoc-static.html'
        super().do_GET()

    def end_headers(self):
        # Add headers to disable caching
        self.send_header('Cache-Control', 'no-store, must-revalidate')
        self.send_header('Expires', '0')
        super().end_headers()


if __name__ == '__main__':
    print(f"Please visit http://localhost:{PORT}")
    http.server.test(
        HandlerClass=NoCacheHTTPRequestHandler,
        port=PORT
    )
