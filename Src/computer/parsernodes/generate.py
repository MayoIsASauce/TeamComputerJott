from pathlib import Path

nodes = [
    "program",
    "func_def",
    "func_def_params",
    "func_def_params_tail",
    "body_statement",
    "return_statement",
    "body",
    "func_body",
    "if_statement",
    "else",
    "else_if",
    "while_loop",
    "func_call",
    "params",
    "params_tail",
    "type",
    "func_return",
    "var_declaration",
    "assignment",
    "bool",
    "operand",
    "expr",
    "id",
    "num",
    "rel_op",
    "math_op",
    "string_literal",
]


def main() -> None:
    with open("Template.java") as f:
        contents: str = f.read()

        for node in nodes:
            name_parts: list[str] = node.split("_")
            classname: str = ""
            for part in name_parts:
                classname += part.title()
            classname += "Node"

            new_file: Path = Path(classname + ".java")
            if new_file.exists():
                continue

            with new_file.open("w") as nf:
                new_contents = contents.replace("Template", classname)
                nf.write(new_contents)

if __name__ == "__main__":
    main()