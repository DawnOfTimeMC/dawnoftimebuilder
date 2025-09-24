import os

def replace_text_in_files(directory_path, old_text, new_text):
    """
    Percorre um diretório, encontra todos os arquivos .json e substitui
    todas as ocorrências de um texto por outro.

    Args:
        directory_path (str): O caminho para o diretório.
        old_text (str): O texto a ser substituído.
        new_text (str): O novo texto que substituirá o antigo.
    """
    if not os.path.isdir(directory_path):
        print(f"Erro: O diretório '{directory_path}' não foi encontrado.")
        return

    print(f"Iniciando a substituição de '{old_text}' por '{new_text}' no diretório: '{directory_path}'...")

    # Percorre todos os arquivos no diretório especificado
    for filename in os.listdir(directory_path):
        if filename.endswith(".json"):
            file_path = os.path.join(directory_path, filename)
            try:
                # Variável para verificar se alguma alteração foi feita
                file_changed = False

                # Abre o arquivo para leitura
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = f.read()

                # Verifica se o texto a ser substituído existe no arquivo
                if old_text in content:
                    # Realiza a substituição
                    modified_content = content.replace(old_text, new_text)
                    file_changed = True

                # Se o arquivo foi alterado, salva o novo conteúdo
                if file_changed:
                    with open(file_path, 'w', encoding='utf-8') as f:
                        f.write(modified_content)
                    print(f"-> O texto foi substituído no arquivo: '{filename}'")
                else:
                    print(f"-> Nenhum texto para substituir em: '{filename}'")

            except Exception as e:
                print(f"-> Ocorreu um erro inesperado ao processar o arquivo '{filename}': {e}")

    print("\nProcessamento concluído.")


# --- MODO DE USAR ---
if __name__ == "__main__":
    # 1. Defina o caminho para a sua pasta de arquivos JSON
    target_directory = "neoforge/src/main/resources/data/dawnoftimebuilder/recipe"

    # 2. Defina o texto a ser encontrado e o novo texto
    text_to_find = "forge"
    text_to_replace = "c"

    # 3. (Opcional) Bloco para criar um diretório e um arquivo de exemplo
    if not os.path.exists(target_directory):
        print(f"O diretório '{target_directory}' não existe. Criando um exemplo...")
        os.makedirs(target_directory)

    # 4. Chama a função principal para processar os arquivos
    replace_text_in_files(target_directory, text_to_find, text_to_replace)