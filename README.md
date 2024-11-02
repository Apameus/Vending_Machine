# Vending Machine

## Functional Requirements

### List all products
```
List all products in a table with columns
ID | NAME | PRICE | QUANTITY 
```

### Retrieve product
```
Input: ID, MONEY

Output:
  ID_NOT_FOUND
  NO_STOCK
  NOT_ENOUGH_MONEY
  SUCCESS ( change )
```

### Update products
```
Authorized User
Can update products: ID, NAME, PRICE, QUANTITY
```

### Get all money inside the machine
```
Input: Authorized User
```

### Analytics
```
Top 3 most brough products
Total Money
```

## Technical Documentation

### High Level Architecture
```mermaid
graph LR
UI[TerminalUI] --> PS[ProductService] --> PR[ProductRepository]
UI --> AS[AnalyticService] --> AR[AnalyticRepository] 
UI --> AUTHS[AuthorizationService] --> AUTHR[AuthorizationRepository]
PS --> AR
```

### List all products
```mermaid
sequenceDiagram
participant UI
participant S as ProductService
participant R as ProductRepository
UI ->> S: listProducts()
S ->> R: findAllProducts()
R ->> S: Product[]
S ->> UI: Product[]
```

### Retrieve product
```mermaid
sequenceDiagram
participant UI
participant PS as ProductService
participant PR as ProductRepository
participant AR as AnalyticRepository

UI ->> UI: id
UI ->> UI: money
UI ->> PS: retrieveProduct( id, money )
PS ->> PR: findProductById( id )
PR ->> PS: Product
alt Product not found
    PS ->> UI: ProductNotFoundException
else Product.quantity == 0
    PS ->> UI: ZeroStockException
else money < Product.price
    PS ->> UI: NotEnoughMoneyException
end
PS ->> PR: decreaseQuantity( id )
PS ->> AR: increaseTotalAmountBy( money - change )
PS ->> UI: Product, change
```

### Update products
```mermaid
sequenceDiagram
participant UI
participant AS as AuthorizationService
participant AR as AuthorizationRepository
        
UI ->> UI: userId (16 digits)
UI ->> AS: authorizeUser( userId )
AS ->> AR: findUserById( userId )
alt User not found
    AR ->> AS: UserNotFoundException
    AS ->> UI: UserNotFoundException
end

UI ->> UI: password
UI ->> AS: authorizeUser ( userId, password )
AS ->> AR: findUserBy( userId, password )
alt Wrong password
    AR ->> AS: AuthorizationFailedException
    AS ->> UI: AuthorizationFailedException
end
```

## Commands
```mermaid
sequenceDiagram
participant UI
participant PS as ProductService
        
UI --> UI: Welcome
loop cmd != 00
    UI ->> UI: cmd
    alt cmd == 01
        UI ->> UI: id
        UI ->> UI: quantity
        UI ->> PS: updateQuantity( id, quantity )
    end
end
```


