import http.server
import socketserver

import torch
from PIL import Image
from io import BytesIO

PORT = 8000

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
print(f"Using device: {device}")

model = torch.hub.load('ultralytics/yolov5', 'custom', path='best.pt').to(device)

class ImageRequestHandler(http.server.SimpleHTTPRequestHandler):

    def do_POST(self):

        content_length = int(self.headers['Content-Length'])
        exceptions = ['llama', 'bee', 'fox', 'ghast', 'detections']

        image = Image.open(BytesIO(self.rfile.read(content_length)))
        results = str(model(image).to(device))[20:]
        results = results[:results.find(':') - 6]
        print(results)
        self.send_response(200)
        self.end_headers()
        exbit = False

        for ex in exceptions:
            if results.find(ex) != -1:
                exbit = True
        
        if not exbit:
            self.wfile.write(results.encode())

with socketserver.TCPServer(("", PORT), ImageRequestHandler) as httpd:
    print(f"Serving on port {PORT}")
    httpd.serve_forever()