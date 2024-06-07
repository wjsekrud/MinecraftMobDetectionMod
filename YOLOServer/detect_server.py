import http.server
import socketserver
import os
from datetime import datetime

import torch
from PIL import Image
from io import BytesIO
from flask import Flask, request, jsonify

# 포트를 지정합니다.
PORT = 8000

# Load custom YOLOv5 model
model = torch.hub.load('ultralytics/yolov5', 'custom', path='1280best.pt')


class ImageRequestHandler(http.server.SimpleHTTPRequestHandler):

    def detect(self,image):

        # Perform detection
        results = model(image)
        print(results)
        return jsonify(results.pandas().xyxy[0].to_dict(orient="records"))
    
    def do_POST(self):

        content_length = int(self.headers['Content-Length'])
        

        image = Image.open(BytesIO(self.rfile.read(content_length)))
        results = str(model(image))[20:]
        results = results[:results.find(':') - 6]
    
        
        print(results)
        self.send_response(200)
        self.end_headers()

        self.wfile.write(results.encode())

        
    

with socketserver.TCPServer(("", PORT), ImageRequestHandler) as httpd:
    print(f"Serving on port {PORT}")
    httpd.serve_forever()