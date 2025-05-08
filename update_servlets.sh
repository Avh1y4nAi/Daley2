#!/bin/bash

# This script updates all servlets to extend BaseServlet..

# Find all servlet files
SERVLET_FILES=$(find src/main/java -name "*Servlet.java" | grep -v "BaseServlet.java")

# Loop through each servlet file
for file in $SERVLET_FILES; do
    echo "Processing $file..."
    
    # Skip PropertyDetailServlet and PropertiesServlet as they've already been updated
    if [[ "$file" == *"PropertyDetailServlet.java" || "$file" == *"PropertiesServlet.java" ]]; then
        echo "Skipping $file (already updated)"
        continue
    fi
    
    # Replace HttpServlet with BaseServlet
    sed -i '' 's/extends HttpServlet/extends BaseServlet/g' "$file"
    
    # Remove import for HttpServlet
    sed -i '' 's/import jakarta.servlet.http.HttpServlet;//g' "$file"
    
    # Add preProcessRequest to doGet method
    sed -i '' '/protected void doGet.*throws ServletException, IOException {/a\\
        // Pre-process request\\
        preProcessRequest(request, response);\\
    ' "$file"
    
    # Add preProcessRequest to doPost method if it exists
    if grep -q "protected void doPost" "$file"; then
        sed -i '' '/protected void doPost.*throws ServletException, IOException {/a\\
        // Pre-process request\\
        preProcessRequest(request, response);\\
        ' "$file"
    fi
    
    # Add super.init() to init method if it exists
    if grep -q "public void init()" "$file"; then
        sed -i '' '/public void init().*throws ServletException {/a\\
        super.init();\\
        ' "$file"
    fi
    
    echo "Updated $file"
done

echo "All servlets updated successfully!"
