///
/// HybridNitroGoogleSsoSpec.hpp
/// This file was generated by nitrogen. DO NOT MODIFY THIS FILE.
/// https://github.com/mrousavy/nitro
/// Copyright © 2025 Marc Rousavy @ Margelo
///

#pragma once

#if __has_include(<NitroModules/HybridObject.hpp>)
#include <NitroModules/HybridObject.hpp>
#else
#error NitroModules cannot be found! Are you sure you installed NitroModules properly?
#endif

// Forward declaration of `NitroGoogleSSOConfig` to properly resolve imports.
namespace margelo::nitro::nitrogooglesso { struct NitroGoogleSSOConfig; }
// Forward declaration of `NitroGoogleUserInfo` to properly resolve imports.
namespace margelo::nitro::nitrogooglesso { struct NitroGoogleUserInfo; }

#include "NitroGoogleSSOConfig.hpp"
#include <NitroModules/Promise.hpp>
#include <optional>
#include "NitroGoogleUserInfo.hpp"

namespace margelo::nitro::nitrogooglesso {

  using namespace margelo::nitro;

  /**
   * An abstract base class for `NitroGoogleSso`
   * Inherit this class to create instances of `HybridNitroGoogleSsoSpec` in C++.
   * You must explicitly call `HybridObject`'s constructor yourself, because it is virtual.
   * @example
   * ```cpp
   * class HybridNitroGoogleSso: public HybridNitroGoogleSsoSpec {
   * public:
   *   HybridNitroGoogleSso(...): HybridObject(TAG) { ... }
   *   // ...
   * };
   * ```
   */
  class HybridNitroGoogleSsoSpec: public virtual HybridObject {
    public:
      // Constructor
      explicit HybridNitroGoogleSsoSpec(): HybridObject(TAG) { }

      // Destructor
      ~HybridNitroGoogleSsoSpec() override = default;

    public:
      // Properties
      

    public:
      // Methods
      virtual void configure(const NitroGoogleSSOConfig& config) = 0;
      virtual std::shared_ptr<Promise<std::optional<NitroGoogleUserInfo>>> signIn() = 0;
      virtual std::shared_ptr<Promise<std::optional<NitroGoogleUserInfo>>> oneTapSignIn() = 0;
      virtual std::shared_ptr<Promise<void>> signOut() = 0;
      virtual std::shared_ptr<Promise<std::optional<NitroGoogleUserInfo>>> getCurrentUser() = 0;

    protected:
      // Hybrid Setup
      void loadHybridMethods() override;

    protected:
      // Tag for logging
      static constexpr auto TAG = "NitroGoogleSso";
  };

} // namespace margelo::nitro::nitrogooglesso
