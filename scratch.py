import os
import glob
import subprocess

base_dir = "/home/noname/Desktop/projects/BUSystem"

files = {
    "Product.java": "product",
    "ProductRepository.java": "product",
    "ProductService.java": "product",
    "InMemoryProductRepository.java": "product",
    "ProductTest.java": "product",
    "ProductServiceTest.java": "product",
    "InMemoryProductRepositoryTest.java": "product",
    "Sale.java": "sale",
    "SaleItem.java": "sale",
    "SaleRepository.java": "sale",
    "SaleStatus.java": "sale",
    "SaleService.java": "sale",
    "InMemorySaleRepository.java": "sale",
    "SaleTest.java": "sale",
    "SaleServiceTest.java": "sale",
    "InMemorySaleRepositoryTest.java": "sale"
}

java_files = glob.glob(f"{base_dir}/src/**/*.java", recursive=True)

moves = []
for p in java_files:
    name = os.path.basename(p)
    if name not in files: continue
    subpkg = files[name]
    
    parts = p.split("/java/")
    rel = parts[1] 
    dir_rel = os.path.dirname(rel)
    old_pkg = dir_rel.replace("/", ".")
    new_dir_rel = f"{dir_rel}/{subpkg}"
    new_pkg = f"{old_pkg}.{subpkg}"
    
    new_dir_abs = os.path.join(base_dir, "src", parts[0].split("/src/")[1], "java", new_dir_rel)
    new_path_abs = os.path.join(new_dir_abs, name)
    moves.append((p, new_path_abs, old_pkg, new_pkg, name))

contents = {}
for p in java_files:
    with open(p, "r") as f:
        contents[p] = f.read()

for p, new_path, old_pkg, new_pkg, name in moves:
    contents[p] = contents[p].replace(f"package {old_pkg};", f"package {new_pkg};")

for _, _, old_pkg, new_pkg, name in moves:
    old_imp = f"import {old_pkg}.{name[:-5]};"
    new_imp = f"import {new_pkg}.{name[:-5]};"
    for p in java_files:
        contents[p] = contents[p].replace(old_imp, new_imp)

def add_import(c, imp):
    if imp in c: return c
    lines = c.split("\n")
    for i, l in enumerate(lines):
        if l.startswith("package "):
            lines.insert(i+1, f"\n{imp}")
            return "\n".join(lines)
    return c

for p in java_files:
    name = os.path.basename(p)
    if "Sale" in name or name == "MainConsoleUI.java" or name == "ProductConsoleUI.java" or name == "MainApp.java":
        if "Product" in contents[p] and name != "Product.java":
            contents[p] = add_import(contents[p], "import com.busystem.domain.product.Product;")
        if "ProductRepository" in contents[p] and name != "ProductRepository.java":
            contents[p] = add_import(contents[p], "import com.busystem.domain.product.ProductRepository;")
        if "Sale" in contents[p] and name != "Sale.java":
            contents[p] = add_import(contents[p], "import com.busystem.domain.sale.Sale;")
        if "SaleItem" in contents[p] and name != "SaleItem.java":
            contents[p] = add_import(contents[p], "import com.busystem.domain.sale.SaleItem;")
        if "SaleStatus" in contents[p] and name != "SaleStatus.java":
            contents[p] = add_import(contents[p], "import com.busystem.domain.sale.SaleStatus;")
        if "SaleRepository" in contents[p] and name != "SaleRepository.java":
            contents[p] = add_import(contents[p], "import com.busystem.domain.sale.SaleRepository;")

for p in java_files:
    with open(p, "w") as f:
        f.write(contents[p])

for old_path, new_path, _, _, _ in moves:
    os.makedirs(os.path.dirname(new_path), exist_ok=True)
    subprocess.run(["git", "mv", old_path, new_path], cwd=base_dir)

print("Python refactor script complete")
