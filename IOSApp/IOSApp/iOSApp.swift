import SwiftUI
import sharedApp

@main
struct iOSApp: App {
    
    private let cacheModule: CacheModule
    private let caseUses: UseCases
    
    //Inicializo la base de datos y los casos de uso que se utilizarán en toda la APP.
    init(){
        self.cacheModule = CacheModule()
        self.caseUses = UseCases(cacheModule: self.cacheModule)
    }
    
	var body: some Scene {
		WindowGroup {
            //Es aquí donde está toda la lógica de navegación y los menus de toda la aplicación.
            NavigationView{
                //La navegación se realiza mediante menú inferior.
                TabView{
                    Despensa(caseUses: caseUses)
                        .tabItem(){
                            //TODO: Poner las imagenes correctas de los iconos de los menus.
                            Image(systemName: "heart.fill")
                            Text("Despensa")
                        }
                    //TODO: Crear Screen de recetas
                    Text("Recetas")
                        .tabItem(){
                            //TODO: Poner las imagenes correctas de los iconos de los menus.
                            Image(systemName: "heart.fill")
                            Text("Recetas")
                        }
                    //TODO: Crear screen de compra.
                    Text("Compra")
                        .tabItem(){
                            //TODO: Poner las imagenes correctas de los iconos de los menus.
                            Image(systemName: "heart.fill")
                            Text("Compra")
                        }                }
            }
        }
    }
}
