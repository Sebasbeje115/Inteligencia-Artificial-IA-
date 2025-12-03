import tensorflow as tf
from tensorflow.keras.preprocessing.image import ImageDataGenerator
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, Dropout
import os

# Configuración de Directorios
directorio_datos = 'dataset'

if not os.path.exists(directorio_datos):
    print("ERROR: No encuentro la carpeta 'dataset'.")
    exit()

# Preprocesamiento de Imágenes
datagen = ImageDataGenerator(
    rescale=1./255,     
    rotation_range=20,       
    width_shift_range=0.2,
    height_shift_range=0.2,
    shear_range=0.2,    
    zoom_range=0.2,    
    horizontal_flip=True,   
    validation_split=0.2     
)

print("Cargando imágenes...")

# Generador de Entrenamiento
train_generator = datagen.flow_from_directory(
    directorio_datos,
    target_size=(150, 150), 
    batch_size=32,
    class_mode='categorical',
    subset='training'
)

# Generador de Validación
validation_generator = datagen.flow_from_directory(
    directorio_datos,
    target_size=(150, 150),
    batch_size=32,
    class_mode='categorical',
    subset='validation'
)

print("Clases detectadas:", train_generator.class_indices)

# Diseño de la Red Neuronal (CNN)
model = Sequential([
    # Capa 1: Ojo básico (detecta bordes y líneas
    Conv2D(32, (3, 3), activation='relu', input_shape=(150, 150, 3)),
    MaxPooling2D(2, 2),
    
    # Capa 2: Ojo intermedio (detecta formas: ojos, nariz)
    Conv2D(64, (3, 3), activation='relu'),
    MaxPooling2D(2, 2),
    
    # Capa 3: Ojo experto (detecta rostros completos)
    Conv2D(128, (3, 3), activation='relu'),
    MaxPooling2D(2, 2),
    
    Flatten(),
    Dense(512, activation='relu'),
    Dropout(0.5), 
    Dense(train_generator.num_classes, activation='softmax')
])

model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

print("INICIANDO ENTRENAMIENTO (Esto puede tardar unos minutos)...")
historial = model.fit(
    train_generator,
    epochs=20,
    validation_data=validation_generator
)

model.save('modelo_reconocimiento.h5')
print("-------------------------------------------------------")
print("¡ENTRENAMIENTO FINALIZADO!")
print("Modelo guardado como: modelo_reconocimiento.h5")