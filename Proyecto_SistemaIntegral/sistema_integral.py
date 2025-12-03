import torch
from PIL import Image
from transformers import TrOCRProcessor, VisionEncoderDecoderModel
from gtts import gTTS
import os

# Configuración del modelo HTR
MODEL_HTR_ID = "microsoft/trocr-large-handwritten"

print("Inicializando modelos...")

# Carga del procesador y el modelo de visión
try:
    processor_htr = TrOCRProcessor.from_pretrained(MODEL_HTR_ID)
    model_htr = VisionEncoderDecoderModel.from_pretrained(MODEL_HTR_ID)
    print(f" HTR ({MODEL_HTR_ID}) cargado.")
except Exception as e:
    print(f" Error al cargar HTR: {e}")
    model_htr = None


def detectar_y_segmentar(image_path: str):
    """ Carga la imagen y simula la segmentación de regiones de texto. """
    try:
        image = Image.open(image_path).convert("RGB")
        print("-> Imagen cargada.")
        return [image]
    except FileNotFoundError:
        print(f"ERROR: Archivo no encontrado en {image_path}")
        return []


def reconocimiento_htr(list_of_image_crops: list) -> str:
    """ Procesa los recortes de imagen con TrOCR para extraer el texto manuscrito. """
    if not model_htr or not list_of_image_crops:
        return ""

    full_text = []
    print("-> Iniciando Reconocimiento HTR...")

    for image in list_of_image_crops:
        # Preprocesamiento de la imagen para el modelo
        pixel_values = processor_htr(image, return_tensors="pt").pixel_values

        # Generación de la transcripción (Inferencia)
        generated_ids = model_htr.generate(pixel_values)

        # Decodificación de IDs a texto legible
        transcribed_text = processor_htr.decode(generated_ids[0], skip_special_tokens=True)
        full_text.append(transcribed_text)

    return " ".join(full_text)


def sintesis_de_voz_tts(final_text: str, output_filename="audio_manuscrito_final.mp3"):
    """ Convierte el texto final a audio utilizando gTTS. """
    if not final_text.strip():
        print(" Texto vacío. Omitiendo generación de audio.")
        return None

    print(f"-> Generando audio para: '{final_text}'")

    try:
        tts = gTTS(text=final_text, lang='es')
        tts.save(output_filename)
        print(f" Audio guardado en: {output_filename}")
        return os.path.abspath(output_filename)

    except Exception as e:
        print(f" ERROR en TTS: {e}")
        return None


def ejecutar_pipeline_htts(image_path: str):
    """ Ejecuta el flujo completo: Carga -> Reconocimiento (HTR) -> Audio (TTS). """
    print("\n--- INICIO PIPELINE ---")

    # Obtener imágenes
    image_crops = detectar_y_segmentar(image_path)
    if not image_crops:
        return

    # Transcribir imagen a texto
    texto_final = reconocimiento_htr(image_crops)
    print(f"\n[Texto Detectado]: {texto_final}")

    if not texto_final.strip():
        return

    # Convertir texto a audio
    sintesis_de_voz_tts(texto_final, "audio.mp3")
    print("\n--- FIN PIPELINE ---")


if __name__ == "__main__":
    EJEMPLO_IMAGEN_RUTA = "manuscrito_ejemplo.jpg"

    if os.path.exists(EJEMPLO_IMAGEN_RUTA):
        ejecutar_pipeline_htts(EJEMPLO_IMAGEN_RUTA)
    else:
        print(f"\n Por favor, coloca una imagen llamada '{EJEMPLO_IMAGEN_RUTA}' para probar.")