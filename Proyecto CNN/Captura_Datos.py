import cv2
import os

nombre_carpeta = 'dataset/Yo_Sebastians'  # CAMBIAR DE ACUERDO AL NOMBRE DE LA PERSONA
indice_camara = 1 # <--- 0= Externa, 1=Interna


if not os.path.exists(nombre_carpeta):
    os.makedirs(nombre_carpeta)

cap = cv2.VideoCapture(indice_camara)
face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')
count = 0

print(f"Iniciando captura para: {nombre_carpeta}")
print("Presiona 'S' para guardar foto | Presiona 'Q' para salir")

while True:
    ret, frame = cap.read()
    if not ret: break
    
    frame = cv2.flip(frame, 1)
    
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)
    rostro_detectado = None

    for (x, y, w, h) in faces:
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
        rostro_temp = frame[y:y+h, x:x+w]
        rostro_detectado = cv2.resize(rostro_temp, (150, 150))

    cv2.putText(frame, f"Guardadas: {count}", (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 255), 2)
    cv2.imshow('Recolector de Rostros', frame)

    k = cv2.waitKey(1) & 0xFF
    if k == ord('s'):
        if rostro_detectado is not None:
            cv2.imwrite(f"{nombre_carpeta}/rostro_{count}.jpg", rostro_detectado)
            count += 1
            print(f"✅ Foto {count} guardada.")
    elif k == ord('q') or count >= 100:
        break

cap.release()
cv2.destroyAllWindows()