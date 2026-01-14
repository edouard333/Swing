# Swing
Librairie gérant des éléments Swing.

# Comment l'utiliser ?
Utiliser par exemple la classe :
```java
import com.phenix.swing.FileDrop;
```

Exemple :
```java
import com.phenix.swing.FileDrop;

void main(String[] args) {
    // Gérer le drag and drop :
    new FileDrop(Component, files -> {
        // ...
    });
}
```
