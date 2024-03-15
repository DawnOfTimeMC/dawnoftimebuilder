import os
from PIL import Image
import tkinter as tk
from tkinter import filedialog
import json

"""
Use this file to convert CTM textures to fusion !
Select the folder that contains the -ctm.png files, and it will export the new texture in the "fusion-overrides" folder.
"""

def convert_file(folder_path, file_name, convert_type_str, export_path):
    test = os.path.join(folder_path, file_name + ".png")
    base_image = Image.open(test)
    ctm_image = Image.open(os.path.join(folder_path, file_name + "-ctm.png"))
    base = base_image.size[0]
    ti = Image.new("RGBA", (base * 5, base))
    ti.paste(base_image, (0, 0))
    base_image.close()
    t2 = ctm_image.crop((0, 0, base, base))
    ti.paste(t2, (base, 0))
    t2.close()
    t3 = ctm_image.crop((base, 0, base * 2, base))
    ti.paste(t3, (base * 2, 0))
    t3.close()
    t4 = ctm_image.crop((0, base, base, base * 2))
    ti.paste(t4,(base*3,0))
    t4.close()
    t5 = ctm_image.crop((base, base, base * 2, base * 2))
    ctm_image.close()
    ti.paste(t5,(base * 4, 0))
    t5.close()
    combined_image = get_combined_image(ti, base)
    ti.close()

    if convert_type_str == "Compact":
        txf = Image.new("RGBA", (base * 5, base))
        tx1 = combined_image.crop((0, 0, base, base))
        txf.paste(tx1, (0, 0))
        tx1.close()
        tx2 = combined_image.crop((base * 2, base * 2, base * 3, base * 3))
        txf.paste(tx2, (base, 0))
        tx2.close()
        tx3 = combined_image.crop((0, base * 2, base, base * 3))
        txf.paste(tx3, (base * 2, 0))
        tx3.close()
        tx4 = combined_image.crop((base * 2, 0, base * 3, base))
        txf.paste(tx4, (base * 3, 0))
        tx4.close()
        tx5 = combined_image.crop((base, base * 4, base * 2, base * 5))
        combined_image.close()
        txf.paste(tx5, (base * 4, 0))
        tx5.close()
        txf.save(os.path.join(export_path, file_name, ".png"))
        txf.close()
        content = {
            "fusion": {
                "type": "connecting",
                "layout": "compact"
            }
        }
        with open(os.path.join(export_path, file_name + ".png.mcmeta"), 'w', encoding='utf-8') as file:
            json.dump(content, file, ensure_ascii=False, indent=4)
    else : # Default "Full"
        combined_image.save(os.path.join(export_path, file_name + ".png"))
        combined_image.close()
        content = {
            "fusion": {
                "type": "connecting",
                "layout": "full"
            }
        }
        with open(os.path.join(export_path, file_name + ".png.mcmeta"), 'w', encoding='utf-8') as file:
            json.dump(content, file, ensure_ascii=False, indent=4)
    
def convert_files(folder_path, convert_type_str):
    folders = folder_path.split("/")
    if "resources" not in folders:
        return "Impossible to find the parent folder 'resources' !"
    else :
        idx = folders.index("resources") + 1
        export_path = os.sep.join(folders[:idx] + ["fusion-overrides"] + folders[idx:])
        # Créer le dossier de destination s'il n'existe pas
        if not os.path.exists(export_path):
            os.makedirs(export_path)
    # Parcourir tous les fichiers du dossier source
    nb = 0
    for file in os.listdir(folder_path):
        if file.endswith("-ctm.png"):
            convert_file(folder_path, file[:-8], convert_type_str, export_path)
            nb += 1
    
    return f"{nb} textures converties avec succès !"

def select_folder():
    global folder_path
    folder_path = filedialog.askdirectory()
    # Activation du bouton "Convertir en Fusion"
    convert_button.config(state=tk.NORMAL)

def convert_to_fusion():
    # Récupérer la sélection du menu déroulant
    convert_type_str = convert_type.get()
    # Code de conversion
    global folder_path
    if folder_path != "":
        output = convert_files(folder_path, convert_type_str)
        result_label.config(text=output)
        convert_button.config(state=tk.DISABLED)

def main():
    global result_label
    global convert_button
    global folder_path
    global convert_type
    folder_path = ""

    root = tk.Tk()
    root.title("CTM Mod - Sélection du dossier des textures")
    
    # Création du label avec le texte demandé
    label = tk.Label(root, text="Select the resource folder that contains the block textures.")
    label.pack(padx=20, pady=20)  # Ajout du padding pour l'espacement
    
    # Menu déroulant pour choisir le type de dossier
    folder_types = ["Full", "Compact"]
    convert_type = tk.StringVar()
    convert_type.set(folder_types[0])  # Par défaut: "Full"
    folder_menu = tk.OptionMenu(root, convert_type, *folder_types)
    folder_menu.pack(padx=20, pady=5)

    # Création du bouton pour sélectionner le dossier
    select_button = tk.Button(root, text="Select Folder", command=select_folder)
    select_button.pack(pady=5)
    
    # Création du bouton pour convertir en fusion (initiallement désactivé)
    convert_button = tk.Button(root, text="Convertir en Fusion", command=convert_to_fusion, state=tk.DISABLED)
    convert_button.pack(pady=5)
    convert_button.config(state=tk.DISABLED)
    
    # Label pour afficher le résultat de la conversion
    result_label = tk.Label(root, text="")
    result_label.pack(pady=5)
    
    # Boucle principale d'exécution de l'interface graphique
    root.mainloop()

def get_combined_image(ti, base):
    if not (base & (base-1) == 0) or base == 0:
        raise ValueError(f"The texture size {base}x{base} is not a multiple of 2.")
    half_base = int(base / 2)
    f1 = ti.crop((0, 0, half_base, half_base))
    f2 = ti.crop((half_base,0,base,half_base))
    f3 = ti.crop((base,0,half_base*3,half_base))
    f4 = ti.crop((half_base*3,0,base*2,half_base))
    f5 = ti.crop((base*2,0,half_base*5,half_base))
    f6 = ti.crop((half_base*5,0,base*3,half_base))
    f7 = ti.crop((base*3,0,half_base*7,half_base))
    f8 = ti.crop((half_base*7,0,base*4,half_base))
    f9 = ti.crop((base*4,0,half_base*9,half_base))
    f10 = ti.crop((half_base*9,0,base*5,half_base))
    f11 = ti.crop((0,half_base,half_base,base))
    f12 = ti.crop((half_base,half_base,base,base))
    f13 = ti.crop((base,half_base,half_base*3,base))
    f14 = ti.crop((half_base*3,half_base,base*2,base))
    f15 = ti.crop((base*2,half_base,half_base*5,base))
    f16 = ti.crop((half_base*5,half_base,base*3,base))
    f17 = ti.crop((base*3,half_base,half_base*7,base))
    f18 = ti.crop((half_base*7,half_base,base*4,base))
    f19 = ti.crop((base*4,half_base,half_base*9,base))
    f20 = ti.crop((half_base*9,half_base,base*5,base))
    tf = Image.new("RGBA", (base * 8, base * 8))
    tf.paste(f1,(0,0))
    tf.paste(f2,(half_base,0))
    tf.paste(f1,(base,0))
    tf.paste(f8,(half_base*3,0))
    tf.paste(f7,(base*2,0))
    tf.paste(f8,(half_base*5,0))
    tf.paste(f7,(base*3,0))
    tf.paste(f2,(half_base*7,0))
    tf.paste(f1,(base*4,0))
    tf.paste(f8,(half_base*9,0))
    tf.paste(f7,(base*5,0))
    tf.paste(f2,(half_base*11,0))
    tf.paste(f5,(base*6,0))
    tf.paste(f10,(half_base*13,0))
    tf.paste(f7,(base*7,0))
    tf.paste(f8,(half_base*15,0))
    tf.paste(f11,(0,half_base))
    tf.paste(f12,(half_base,half_base))
    tf.paste(f11,(base,half_base))
    tf.paste(f18,(half_base*3,half_base))
    tf.paste(f17,(base*2,half_base))
    tf.paste(f18,(half_base*5,half_base))
    tf.paste(f17,(base*3,half_base))
    tf.paste(f12,(half_base*7,half_base))
    tf.paste(f15,(base*4,half_base))
    tf.paste(f20,(half_base*9,half_base))
    tf.paste(f19,(base*5,half_base))
    tf.paste(f16,(half_base*11,half_base))
    tf.paste(f15,(base*6,half_base))
    tf.paste(f20,(half_base*13,half_base))
    tf.paste(f19,(base*7,half_base))
    tf.paste(f20,(half_base*15,half_base))
    tf.paste(f1,(0,base))
    tf.paste(f2,(half_base,base))
    tf.paste(f1,(base,base))
    f1.close()
    tf.paste(f8,(half_base*3,base))
    tf.paste(f7,(base*2,base))
    tf.paste(f8,(half_base*5,base))
    tf.paste(f7,(base*3,base))
    tf.paste(f2,(half_base*7,base))
    f2.close()
    tf.paste(f5,(base*4,base))
    tf.paste(f10,(half_base*9,base))
    tf.paste(f9,(base*5,base))
    tf.paste(f6,(half_base*11,base))
    tf.paste(f9,(base*6,base))
    tf.paste(f10,(half_base*13,base))
    tf.paste(f9,(base*7,base))
    tf.paste(f6,(half_base*15,base))
    tf.paste(f15,(0,half_base*3))
    tf.paste(f16,(half_base,half_base*3))
    tf.paste(f15,(base,half_base*3))
    tf.paste(f14,(half_base*3,half_base*3))
    tf.paste(f13,(base*2,half_base*3))
    tf.paste(f14,(half_base*5,half_base*3))
    tf.paste(f13,(base*3,half_base*3))
    tf.paste(f16,(half_base*7,half_base*3))
    tf.paste(f11,(base*4,half_base*3))
    tf.paste(f18,(half_base*9,half_base*3))
    tf.paste(f17,(base*5,half_base*3))
    tf.paste(f12,(half_base*11,half_base*3))
    tf.paste(f17,(base*6,half_base*3))
    tf.paste(f18,(half_base*13,half_base*3))
    tf.paste(f19,(base*7,half_base*3))
    tf.paste(f16,(half_base*15,half_base*3))
    tf.paste(f5,(0,base*2))
    tf.paste(f6,(half_base,base*2))
    tf.paste(f5,(base,base*2))
    tf.paste(f4,(half_base*3,base*2))
    tf.paste(f3,(base*2,base*2))
    tf.paste(f4,(half_base*5,base*2))
    tf.paste(f3,(base*3,base*2))
    tf.paste(f6,(half_base*7,base*2))
    tf.paste(f5,(base*4,base*2))
    tf.paste(f4,(half_base*9,base*2))
    tf.paste(f7,(base*5,base*2))
    tf.paste(f8,(half_base*11,base*2))
    tf.paste(f5,(base*6,base*2))
    tf.paste(f10,(half_base*13,base*2))
    tf.paste(f7,(base*7,base*2))
    f7.close()
    tf.paste(f8,(half_base*15,base*2))
    f8.close()
    tf.paste(f15,(0,half_base*5))
    tf.paste(f16,(half_base,half_base*5))
    tf.paste(f15,(base,half_base*5))
    tf.paste(f14,(half_base*3,half_base*5))
    tf.paste(f13,(base*2,half_base*5))
    tf.paste(f14,(half_base*5,half_base*5))
    tf.paste(f13,(base*3,half_base*5))
    tf.paste(f16,(half_base*7,half_base*5))
    tf.paste(f15,(base*4,half_base*5))
    tf.paste(f20,(half_base*9,half_base*5))
    tf.paste(f19,(base*5,half_base*5))
    tf.paste(f14,(half_base*11,half_base*5))
    tf.paste(f15,(base*6,half_base*5))
    f15.close()
    tf.paste(f14,(half_base*13,half_base*5))
    tf.paste(f13,(base*7,half_base*5))
    tf.paste(f20,(half_base*15,half_base*5))
    tf.paste(f5,(0,base*3))
    tf.paste(f6,(half_base,base*3))
    tf.paste(f5,(base,base*3))
    f5.close()
    tf.paste(f4,(half_base*3,base*3))
    tf.paste(f3,(base*2,base*3))
    tf.paste(f4,(half_base*5,base*3))
    tf.paste(f3,(base*3,base*3))
    tf.paste(f6,(half_base*7,base*3))
    tf.paste(f3,(base*4,base*3))
    tf.paste(f10,(half_base*9,base*3))
    tf.paste(f9,(base*5,base*3))
    tf.paste(f6,(half_base*11,base*3))
    tf.paste(f9,(base*6,base*3))
    tf.paste(f4,(half_base*13,base*3))
    tf.paste(f3,(base*7,base*3))
    tf.paste(f6,(half_base*15,base*3))
    f6.close()
    tf.paste(f11,(0,half_base*7))
    tf.paste(f12,(half_base,half_base*7))
    tf.paste(f11,(base,half_base*7))
    f11.close()
    tf.paste(f18,(half_base*3,half_base*7))
    tf.paste(f17,(base*2,half_base*7))
    tf.paste(f18,(half_base*5,half_base*7))
    tf.paste(f17,(base*3,half_base*7))
    tf.paste(f12,(half_base*7,half_base*7))
    f12.close()
    tf.paste(f17,(base*4,half_base*7))
    tf.paste(f18,(half_base*9,half_base*7))
    tf.paste(f13,(base*5,half_base*7))
    tf.paste(f16,(half_base*11,half_base*7))
    tf.paste(f17,(base*6,half_base*7))
    f17.close()
    tf.paste(f18,(half_base*13,half_base*7))
    f18.close()
    tf.paste(f19,(base*7,half_base*7))
    tf.paste(f16,(half_base*15,half_base*7))
    f16.close()
    tf.paste(f9,(0,base*4))
    tf.paste(f4,(half_base,base*4))
    tf.paste(f9,(base,base*4))
    tf.paste(f10,(half_base*3,base*4))
    tf.paste(f9,(base*2,base*4))
    tf.paste(f4,(half_base*5,base*4))
    tf.paste(f9,(base*3,base*4))
    tf.paste(f10,(half_base*7,base*4))
    tf.paste(f9,(base*4,base*4))
    tf.paste(f10,(half_base*9,base*4))
    tf.paste(f9,(base*5,base*4))
    tf.paste(f10,(half_base*11,base*4))
    tf.paste(f3,(base*6,base*4))
    tf.paste(f4,(half_base*13,base*4))
    tf.paste(f3,(base*7,base*4))
    tf.paste(f4,(half_base*15,base*4))
    tf.paste(f13,(0,half_base*9))
    tf.paste(f20,(half_base,half_base*9))
    tf.paste(f19,(base,half_base*9))
    tf.paste(f20,(half_base*3,half_base*9))
    tf.paste(f19,(base*2,half_base*9))
    tf.paste(f14,(half_base*5,half_base*9))
    tf.paste(f13,(base*3,half_base*9))
    tf.paste(f14,(half_base*7,half_base*9))
    tf.paste(f19,(base*4,half_base*9))
    tf.paste(f14,(half_base*9,half_base*9))
    tf.paste(f13,(base*5,half_base*9))
    tf.paste(f20,(half_base*11,half_base*9))
    tf.paste(f13,(base*6,half_base*9))
    tf.paste(f20,(half_base*13,half_base*9))
    tf.paste(f19,(base*7,half_base*9))
    tf.paste(f14,(half_base*15,half_base*9))
    tf.paste(f3,(0,base*5))
    tf.paste(f10,(half_base,base*5))
    tf.paste(f3,(base*2,base*5))
    tf.paste(f4,(half_base*5,base*5))
    tf.paste(f3,(base*3,base*5))
    tf.paste(f10,(half_base*7,base*5))
    tf.paste(f9,(base*4,base*5))
    tf.paste(f4,(half_base*9,base*5))
    tf.paste(f3,(base*5,base*5))
    tf.paste(f10,(half_base*11,base*5))
    tf.paste(f3,(base*6,base*5))
    f3.close()
    tf.paste(f10,(half_base*13,base*5))
    f10.close()
    tf.paste(f9,(base*7,base*5))
    f9.close()
    tf.paste(f4,(half_base*15,base*5))
    f4.close()
    tf.paste(f19,(0,half_base*11))
    tf.paste(f14,(half_base,half_base*11))
    tf.paste(f19,(base*2,half_base*11))
    tf.paste(f20,(half_base*5,half_base*11))
    tf.paste(f13,(base*3,half_base*11))
    tf.paste(f20,(half_base*7,half_base*11))
    tf.paste(f19,(base*4,half_base*11))
    tf.paste(f20,(half_base*9,half_base*11))
    tf.paste(f19,(base*5,half_base*11))
    f19.close()
    tf.paste(f20,(half_base*11,half_base*11))
    f20.close()
    tf.paste(f13,(base*6,half_base*11))
    tf.paste(f14,(half_base*13,half_base*11))
    tf.paste(f13,(base*7,half_base*11))
    f13.close()
    tf.paste(f14,(half_base*15,half_base*11))
    f14.close()
    return tf

if __name__ == "__main__":
    main()