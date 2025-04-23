import os

# Directory to save the files
output_dir = "./"
os.makedirs(output_dir, exist_ok=True)

# Function to generate infix expressions
def generate_expression(length):
    return "1" + "+1" * (length - 1)

# Lengths of the expressions
lengths = [10, 100, 1000, 10000, 100000, 1000000, 10000000]

# Generate and save expressions
for length in lengths:
    filename = os.path.join(output_dir, f"expression_{length}.txt")
    with open(filename, "w") as file:
        file.write(generate_expression(length))

print(f"Expressions generated and saved in {output_dir}")