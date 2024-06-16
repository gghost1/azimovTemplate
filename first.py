from io import BytesIO
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from flask import Flask, request, send_file, jsonify

app = Flask(__name__)

@app.route('/cv', methods=['POST'])
def convert_vector():
    buffer = BytesIO()  # Создаем буфер в памяти для сохранения PDF
    c = canvas.Canvas(buffer, pagesize=letter)

    # Добавление текста в PDF
    c.drawString(100, 750, "Hello, World!")

    # Сохранение PDF в буфере
    c.save()

    # Получение содержимого буфера в виде bytes
    pdf_bytes = buffer.getvalue()
    buffer.close()

    return pdf_bytes  # Возвращаем байтовый массив PDF файла

if __name__ == 'main':
    app.run(debug=True, port=3000)