import cv2
import numpy as np
from tensorflow.keras.models import load_model
import os


indice_camara = 0  # <--- 0= Externa, 1=Interna


print("Cargando la Inteligencia Artificial...")
model = load_model('modelo_reconocimiento.h5')

# Cargar los nombres de las clases
clases = sorted(os.listdir('dataset'))
print("Clases reconocidas:", clases)

cap = cv2.VideoCapture(indice_camara)
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

while True:
    ret, frame = cap.read()
    if not ret: break

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x, y, w, h) in faces:
        rostro = frame[y:y+h, x:x+w]
        rostro = cv2.resize(rostro, (150, 150))
        
        rostro = rostro.astype("float") / 255.0
        rostro = np.expand_dims(rostro, axis=0)

        prediccion = model.predict(rostro, verbose=0)
        indice = np.argmax(prediccion)
        probabilidad = prediccion[0][indice]
        
        etiqueta = clases[indice]
        
        if probabilidad > 0.8:
            color = (0, 255, 0) # Verde
            texto = f"{etiqueta}: {int(probabilidad*100)}%"
        else:
            color = (0, 0, 255) # Rojo
            texto = f"¿{etiqueta}?: {int(probabilidad*100)}%"

        cv2.rectangle(frame, (x, y), (x+w, y+h), color, 2)
        cv2.putText(frame, texto, (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.8, color, 2)

    cv2.imshow('CNN Facial Recognition - Tiempo Real (Estable)', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()