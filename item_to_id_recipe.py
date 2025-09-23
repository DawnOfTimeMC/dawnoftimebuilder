import os
import json

def replace_item_in_result_recursively(data, is_inside_result=False):
    """
    Percorre recursivamente um objeto Python e substitui a chave 'item' por 'id'
    APENAS se estiver dentro de uma chave 'result'.

    Args:
        data: O objeto Python (dicionário ou lista) para processar.
        is_inside_result: Um booleano que indica se a execução atual está
                          dentro de uma chave 'result'.

    Returns:
        O objeto modificado.
    """
    if isinstance(data, dict):
        new_dict = {}
        for key, value in data.items():
            # A condição para renomear a chave agora é dupla:
            # 1. A chave deve ser "item".
            # 2. O flag "is_inside_result" deve ser verdadeiro.
            new_key = "id" if key == "item" and is_inside_result else key

            # O próximo nível da recursão estará "dentro de result" se a chave
            # atual for "result" ou se já estávamos dentro.
            new_value = replace_item_in_result_recursively(value, is_inside_result or key == "result")

            new_dict[new_key] = new_value
        return new_dict

    elif isinstance(data, list):
        # Se for uma lista, apenas propaga o status de "is_inside_result" para os elementos
        return [replace_item_in_result_recursively(element, is_inside_result) for element in data]

    else:
        # Retorna o valor como está se não for dicionário nem lista
        return data

def process_json_files_in_directory(directory_path):
    """
    Percorre todos os arquivos em um diretório e, para cada arquivo .json,
    substitui 'item' por 'id' somente dentro da chave 'result'.

    Args:
        directory_path: O caminho para o diretório contendo os arquivos JSON.
    """
    if not os.path.isdir(directory_path):
        print(f"Erro: O diretório '{directory_path}' não foi encontrado.")
        return

    print(f"Iniciando processamento no diretório: '{directory_path}'...")

    for filename in os.listdir(directory_path):
        if filename.endswith(".json"):
            file_path = os.path.join(directory_path, filename)
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    content = json.load(f)

                # Chama a nova função para modificar o conteúdo
                modified_content = replace_item_in_result_recursively(content)

                with open(file_path, 'w', encoding='utf-8') as f:
                    json.dump(modified_content, f, indent=4)

                print(f"-> Arquivo '{filename}' processado com sucesso.")

            except json.JSONDecodeError:
                print(f"-> Erro: O arquivo '{filename}' não é um JSON válido e foi ignorado.")
            except Exception as e:
                print(f"-> Ocorreu um erro inesperado ao processar o arquivo '{filename}': {e}")

    print("\nProcessamento concluído.")

# --- MODO DE USAR ---
if __name__ == "__main__":
    # 1. Defina o caminho para a sua pasta de arquivos JSON aqui
    target_directory = "neoforge/src/main/resources/data/dawnoftimebuilder/recipes"  # Altere este valor para o seu diretório

    # 2. Bloco para criar um exemplo caso o diretório não exista
    if not os.path.exists(target_directory):
        print(f"O diretório '{target_directory}' não existe. Criando um exemplo...")
        os.makedirs(target_directory)
        example_json_content = {
            "type": "minecraft:crafting_shaped",
            "pattern": ["I", "i"],
            "key": {
                "I": {"item": "minecraft:stripped_acacia_log"},
                "i": {"item": "minecraft:red_sandstone"}
            },
            "result": {"item": "dawnoftimebuilder:acacia_beam", "count": 4}
        }
        with open(os.path.join(target_directory, 'acacia_beam.json'), 'w', encoding='utf-8') as f:
            json.dump(example_json_content, f, indent=4)
        print("Diretório de exemplo e arquivo 'acacia_beam.json' criados.")

    # 3. Chama a função principal para processar os arquivos
    process_json_files_in_directory(target_directory)

    # 4. (Opcional) Imprime o resultado para verificação
    print("\n--- Conteúdo do arquivo 'acacia_beam.json' após a modificação ---")
    with open(os.path.join(target_directory, 'acacia_beam.json'), 'r', encoding='utf-8') as f:
        print(f.read())