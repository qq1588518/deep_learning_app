// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/protobuf/compiler/cpp/cpp_test_large_enum_value.proto

#define INTERNAL_SUPPRESS_PROTOBUF_FIELD_DEPRECATION
#include <google/protobuf/compiler/cpp/cpp_test_large_enum_value.pb.h>

#include <algorithm>

#include <google/protobuf/stubs/common.h>
#include <google/protobuf/stubs/port.h>
#include <google/protobuf/stubs/once.h>
#include <google/protobuf/io/coded_stream.h>
#include <google/protobuf/wire_format_lite_inl.h>
#include <google/protobuf/descriptor.h>
#include <google/protobuf/generated_message_reflection.h>
#include <google/protobuf/reflection_ops.h>
#include <google/protobuf/wire_format.h>
// @@protoc_insertion_point(includes)

namespace protobuf_unittest {
class TestLargeEnumValueDefaultTypeInternal : public ::google::protobuf::internal::ExplicitlyConstructed<TestLargeEnumValue> {
} _TestLargeEnumValue_default_instance_;

namespace protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto {


namespace {

::google::protobuf::Metadata file_level_metadata[1];
const ::google::protobuf::EnumDescriptor* file_level_enum_descriptors[1];

}  // namespace

PROTOBUF_CONSTEXPR_VAR ::google::protobuf::internal::ParseTableField
    const TableStruct::entries[] = {
  {0, 0, 0, ::google::protobuf::internal::kInvalidMask, 0, 0},
};

PROTOBUF_CONSTEXPR_VAR ::google::protobuf::internal::AuxillaryParseTableField
    const TableStruct::aux[] = {
  ::google::protobuf::internal::AuxillaryParseTableField(),
};
PROTOBUF_CONSTEXPR_VAR ::google::protobuf::internal::ParseTable const
    TableStruct::schema[] = {
  { NULL, NULL, 0, -1, -1, false },
};

const ::google::protobuf::uint32 TableStruct::offsets[] = {
  GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TestLargeEnumValue, _has_bits_),
  GOOGLE_PROTOBUF_GENERATED_MESSAGE_FIELD_OFFSET(TestLargeEnumValue, _internal_metadata_),
  ~0u,  // no _extensions_
  ~0u,  // no _oneof_case_
  ~0u,  // no _weak_field_map_
};

static const ::google::protobuf::internal::MigrationSchema schemas[] = {
  { 0, 5, sizeof(TestLargeEnumValue)},
};

static ::google::protobuf::Message const * const file_default_instances[] = {
  reinterpret_cast<const ::google::protobuf::Message*>(&_TestLargeEnumValue_default_instance_),
};

namespace {

void protobuf_AssignDescriptors() {
  AddDescriptors();
  ::google::protobuf::MessageFactory* factory = NULL;
  AssignDescriptors(
      "google/protobuf/compiler/cpp/cpp_test_large_enum_value.proto", schemas, file_default_instances, TableStruct::offsets, factory,
      file_level_metadata, file_level_enum_descriptors, NULL);
}

void protobuf_AssignDescriptorsOnce() {
  static GOOGLE_PROTOBUF_DECLARE_ONCE(once);
  ::google::protobuf::GoogleOnceInit(&once, &protobuf_AssignDescriptors);
}

void protobuf_RegisterTypes(const ::std::string&) GOOGLE_ATTRIBUTE_COLD;
void protobuf_RegisterTypes(const ::std::string&) {
  protobuf_AssignDescriptorsOnce();
  ::google::protobuf::internal::RegisterAllTypes(file_level_metadata, 1);
}

}  // namespace

void TableStruct::Shutdown() {
  _TestLargeEnumValue_default_instance_.Shutdown();
  delete file_level_metadata[0].reflection;
}

void TableStruct::InitDefaultsImpl() {
  GOOGLE_PROTOBUF_VERIFY_VERSION;

  ::google::protobuf::internal::InitProtobufDefaults();
  _TestLargeEnumValue_default_instance_.DefaultConstruct();
}

void InitDefaults() {
  static GOOGLE_PROTOBUF_DECLARE_ONCE(once);
  ::google::protobuf::GoogleOnceInit(&once, &TableStruct::InitDefaultsImpl);
}
void AddDescriptorsImpl() {
  InitDefaults();
  static const char descriptor[] = {
      "\n<google/protobuf/compiler/cpp/cpp_test_"
      "large_enum_value.proto\022\021protobuf_unittes"
      "t\"J\n\022TestLargeEnumValue\"4\n\022EnumWithLarge"
      "Value\022\013\n\007VALUE_1\020\001\022\021\n\tVALUE_MAX\020\377\377\377\377\007"
  };
  ::google::protobuf::DescriptorPool::InternalAddGeneratedFile(
      descriptor, 157);
  ::google::protobuf::MessageFactory::InternalRegisterGeneratedFile(
    "google/protobuf/compiler/cpp/cpp_test_large_enum_value.proto", &protobuf_RegisterTypes);
  ::google::protobuf::internal::OnShutdown(&TableStruct::Shutdown);
}

void AddDescriptors() {
  static GOOGLE_PROTOBUF_DECLARE_ONCE(once);
  ::google::protobuf::GoogleOnceInit(&once, &AddDescriptorsImpl);
}
// Force AddDescriptors() to be called at static initialization time.
struct StaticDescriptorInitializer {
  StaticDescriptorInitializer() {
    AddDescriptors();
  }
} static_descriptor_initializer;

}  // namespace protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto

const ::google::protobuf::EnumDescriptor* TestLargeEnumValue_EnumWithLargeValue_descriptor() {
  protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::protobuf_AssignDescriptorsOnce();
  return protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::file_level_enum_descriptors[0];
}
bool TestLargeEnumValue_EnumWithLargeValue_IsValid(int value) {
  switch (value) {
    case 1:
    case 2147483647:
      return true;
    default:
      return false;
  }
}

#if !defined(_MSC_VER) || _MSC_VER >= 1900
const TestLargeEnumValue_EnumWithLargeValue TestLargeEnumValue::VALUE_1;
const TestLargeEnumValue_EnumWithLargeValue TestLargeEnumValue::VALUE_MAX;
const TestLargeEnumValue_EnumWithLargeValue TestLargeEnumValue::EnumWithLargeValue_MIN;
const TestLargeEnumValue_EnumWithLargeValue TestLargeEnumValue::EnumWithLargeValue_MAX;
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

// ===================================================================

#if !defined(_MSC_VER) || _MSC_VER >= 1900
#endif  // !defined(_MSC_VER) || _MSC_VER >= 1900

TestLargeEnumValue::TestLargeEnumValue()
  : ::google::protobuf::Message(), _internal_metadata_(NULL) {
  if (GOOGLE_PREDICT_TRUE(this != internal_default_instance())) {
    protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::InitDefaults();
  }
  SharedCtor();
  // @@protoc_insertion_point(constructor:protobuf_unittest.TestLargeEnumValue)
}
TestLargeEnumValue::TestLargeEnumValue(const TestLargeEnumValue& from)
  : ::google::protobuf::Message(),
      _internal_metadata_(NULL),
      _has_bits_(from._has_bits_),
      _cached_size_(0) {
  _internal_metadata_.MergeFrom(from._internal_metadata_);
  // @@protoc_insertion_point(copy_constructor:protobuf_unittest.TestLargeEnumValue)
}

void TestLargeEnumValue::SharedCtor() {
  _cached_size_ = 0;
}

TestLargeEnumValue::~TestLargeEnumValue() {
  // @@protoc_insertion_point(destructor:protobuf_unittest.TestLargeEnumValue)
  SharedDtor();
}

void TestLargeEnumValue::SharedDtor() {
}

void TestLargeEnumValue::SetCachedSize(int size) const {
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
}
const ::google::protobuf::Descriptor* TestLargeEnumValue::descriptor() {
  protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::protobuf_AssignDescriptorsOnce();
  return protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::file_level_metadata[kIndexInFileMessages].descriptor;
}

const TestLargeEnumValue& TestLargeEnumValue::default_instance() {
  protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::InitDefaults();
  return *internal_default_instance();
}

TestLargeEnumValue* TestLargeEnumValue::New(::google::protobuf::Arena* arena) const {
  TestLargeEnumValue* n = new TestLargeEnumValue;
  if (arena != NULL) {
    arena->Own(n);
  }
  return n;
}

void TestLargeEnumValue::Clear() {
// @@protoc_insertion_point(message_clear_start:protobuf_unittest.TestLargeEnumValue)
  _has_bits_.Clear();
  _internal_metadata_.Clear();
}

bool TestLargeEnumValue::MergePartialFromCodedStream(
    ::google::protobuf::io::CodedInputStream* input) {
#define DO_(EXPRESSION) if (!GOOGLE_PREDICT_TRUE(EXPRESSION)) goto failure
  ::google::protobuf::uint32 tag;
  // @@protoc_insertion_point(parse_start:protobuf_unittest.TestLargeEnumValue)
  for (;;) {
    ::std::pair< ::google::protobuf::uint32, bool> p = input->ReadTagWithCutoffNoLastTag(127u);
    tag = p.first;
    if (!p.second) goto handle_unusual;
  handle_unusual:
    if (tag == 0 ||
        ::google::protobuf::internal::WireFormatLite::GetTagWireType(tag) ==
        ::google::protobuf::internal::WireFormatLite::WIRETYPE_END_GROUP) {
      goto success;
    }
    DO_(::google::protobuf::internal::WireFormat::SkipField(
          input, tag, mutable_unknown_fields()));
  }
success:
  // @@protoc_insertion_point(parse_success:protobuf_unittest.TestLargeEnumValue)
  return true;
failure:
  // @@protoc_insertion_point(parse_failure:protobuf_unittest.TestLargeEnumValue)
  return false;
#undef DO_
}

void TestLargeEnumValue::SerializeWithCachedSizes(
    ::google::protobuf::io::CodedOutputStream* output) const {
  // @@protoc_insertion_point(serialize_start:protobuf_unittest.TestLargeEnumValue)
  ::google::protobuf::uint32 cached_has_bits = 0;
  (void) cached_has_bits;

  if (_internal_metadata_.have_unknown_fields()) {
    ::google::protobuf::internal::WireFormat::SerializeUnknownFields(
        unknown_fields(), output);
  }
  // @@protoc_insertion_point(serialize_end:protobuf_unittest.TestLargeEnumValue)
}

::google::protobuf::uint8* TestLargeEnumValue::InternalSerializeWithCachedSizesToArray(
    bool deterministic, ::google::protobuf::uint8* target) const {
  // @@protoc_insertion_point(serialize_to_array_start:protobuf_unittest.TestLargeEnumValue)
  ::google::protobuf::uint32 cached_has_bits = 0;
  (void) cached_has_bits;

  if (_internal_metadata_.have_unknown_fields()) {
    target = ::google::protobuf::internal::WireFormat::SerializeUnknownFieldsToArray(
        unknown_fields(), target);
  }
  // @@protoc_insertion_point(serialize_to_array_end:protobuf_unittest.TestLargeEnumValue)
  return target;
}

size_t TestLargeEnumValue::ByteSizeLong() const {
// @@protoc_insertion_point(message_byte_size_start:protobuf_unittest.TestLargeEnumValue)
  size_t total_size = 0;

  if (_internal_metadata_.have_unknown_fields()) {
    total_size +=
      ::google::protobuf::internal::WireFormat::ComputeUnknownFieldsSize(
        unknown_fields());
  }
  int cached_size = ::google::protobuf::internal::ToCachedSize(total_size);
  GOOGLE_SAFE_CONCURRENT_WRITES_BEGIN();
  _cached_size_ = cached_size;
  GOOGLE_SAFE_CONCURRENT_WRITES_END();
  return total_size;
}

void TestLargeEnumValue::MergeFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_merge_from_start:protobuf_unittest.TestLargeEnumValue)
  GOOGLE_DCHECK_NE(&from, this);
  const TestLargeEnumValue* source =
      ::google::protobuf::internal::DynamicCastToGenerated<const TestLargeEnumValue>(
          &from);
  if (source == NULL) {
  // @@protoc_insertion_point(generalized_merge_from_cast_fail:protobuf_unittest.TestLargeEnumValue)
    ::google::protobuf::internal::ReflectionOps::Merge(from, this);
  } else {
  // @@protoc_insertion_point(generalized_merge_from_cast_success:protobuf_unittest.TestLargeEnumValue)
    MergeFrom(*source);
  }
}

void TestLargeEnumValue::MergeFrom(const TestLargeEnumValue& from) {
// @@protoc_insertion_point(class_specific_merge_from_start:protobuf_unittest.TestLargeEnumValue)
  GOOGLE_DCHECK_NE(&from, this);
  _internal_metadata_.MergeFrom(from._internal_metadata_);
  ::google::protobuf::uint32 cached_has_bits = 0;
  (void) cached_has_bits;

}

void TestLargeEnumValue::CopyFrom(const ::google::protobuf::Message& from) {
// @@protoc_insertion_point(generalized_copy_from_start:protobuf_unittest.TestLargeEnumValue)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

void TestLargeEnumValue::CopyFrom(const TestLargeEnumValue& from) {
// @@protoc_insertion_point(class_specific_copy_from_start:protobuf_unittest.TestLargeEnumValue)
  if (&from == this) return;
  Clear();
  MergeFrom(from);
}

bool TestLargeEnumValue::IsInitialized() const {
  return true;
}

void TestLargeEnumValue::Swap(TestLargeEnumValue* other) {
  if (other == this) return;
  InternalSwap(other);
}
void TestLargeEnumValue::InternalSwap(TestLargeEnumValue* other) {
  std::swap(_has_bits_[0], other->_has_bits_[0]);
  _internal_metadata_.Swap(&other->_internal_metadata_);
  std::swap(_cached_size_, other->_cached_size_);
}

::google::protobuf::Metadata TestLargeEnumValue::GetMetadata() const {
  protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::protobuf_AssignDescriptorsOnce();
  return protobuf_google_2fprotobuf_2fcompiler_2fcpp_2fcpp_5ftest_5flarge_5fenum_5fvalue_2eproto::file_level_metadata[kIndexInFileMessages];
}

#if PROTOBUF_INLINE_NOT_IN_HEADERS
// TestLargeEnumValue

#endif  // PROTOBUF_INLINE_NOT_IN_HEADERS

// @@protoc_insertion_point(namespace_scope)

}  // namespace protobuf_unittest

// @@protoc_insertion_point(global_scope)
